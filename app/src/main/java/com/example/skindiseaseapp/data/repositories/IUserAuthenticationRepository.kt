package com.oguzdogdu.walliescompose.data.repository

import com.example.skindiseaseapp.domain.model.auth.User
import com.example.skindiseaseapp.domain.wrapper.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface IUserAuthenticationRepository {
    suspend fun isUserAuthenticatedInFirebase(): Flow<Boolean>
    suspend fun isUserAuthenticatedWithGoogle(): Flow<Boolean>
    suspend fun signInWithGoogle(idToken: String?):Flow<Resource<AuthResult>>
    suspend fun fetchUserInfos():Flow<Resource<User?>>
    suspend fun signOut()
    suspend fun deleteAccount()
}