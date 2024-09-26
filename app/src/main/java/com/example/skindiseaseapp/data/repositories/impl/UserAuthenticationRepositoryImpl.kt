package com.example.skindiseaseapp.data.repositories.impl

import com.example.skindiseaseapp.core.utils.helper.common.Constants.COLLECTION_PATH
import com.example.skindiseaseapp.core.utils.helper.common.Constants.EMAIL
import com.example.skindiseaseapp.core.utils.helper.common.Constants.ID
import com.example.skindiseaseapp.core.utils.helper.common.Constants.IMAGE
import com.example.skindiseaseapp.core.utils.helper.common.Constants.NAME
import com.example.skindiseaseapp.core.utils.helper.common.Constants.SURNAME
import com.example.skindiseaseapp.domain.model.auth.User
import com.example.skindiseaseapp.domain.model.auth.toUserDomain
import com.example.skindiseaseapp.domain.wrapper.Resource
import com.example.skindiseaseapp.domain.wrapper.toResource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.oguzdogdu.walliescompose.data.repository.IUserAuthenticationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.collections.any
import kotlin.let
import kotlin.text.orEmpty
import kotlin.to
import kotlin.toString

class UserAuthenticationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
//    @Dispatcher(SkinAppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    ) : IUserAuthenticationRepository {

    override suspend fun isUserAuthenticatedInFirebase(): Flow<Boolean> = flow {
//        withContext(ioDispatcher){
//
//        }
        emit(auth.currentUser != null)
    }

    override suspend fun isUserAuthenticatedWithGoogle(): Flow<Boolean> = flow {
        val user = FirebaseAuth.getInstance().currentUser
        emit(user?.providerData?.any { it.providerId == GoogleAuthProvider.PROVIDER_ID } == true)
    }

    override suspend fun signInWithGoogle(idToken: String?): Flow<Resource<AuthResult>> {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return flowOf(auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = FirebaseAuth.getInstance().currentUser
                val userModel = hashMapOf(
                    ID to auth.currentUser?.uid,
                    EMAIL to user?.email.orEmpty(),
                    NAME to user?.displayName.orEmpty(),
                    IMAGE to user?.photoUrl.toString()
                )
                auth.currentUser?.uid?.let {
                    firebaseFirestore.collection(COLLECTION_PATH).document(it)
                        .set(userModel)
                }
            }
        }.await()).toResource()
    }

    override suspend fun fetchUserInfos(): Flow<Resource<User?>> {
        val user = FirebaseAuth.getInstance().currentUser
        val id = user?.uid ?: ""

        val db = FirebaseFirestore.getInstance()
        val userDocument = db.collection(COLLECTION_PATH).document(id).get().await()

        val name = userDocument?.getString(NAME)
        val email = userDocument?.getString(EMAIL)
        val profileImageUrl = userDocument?.getString(IMAGE)
        val surname = userDocument?.getString(SURNAME)
//        val favorites = userDocument?.get(FAVORITES) as? List<HashMap<String, String>>?

        val result = User(
            name = name,
            surname = surname,
            email = email,
            image = profileImageUrl,
        )
        return flowOf(result.toUserDomain()).toResource()
    }

    override suspend fun signOut() = auth.signOut()

    override suspend fun deleteAccount() {
        TODO("Not yet implemented")
    }
}