package com.example.skindiseaseapp.data.repository

import com.example.skindiseaseapp.data.di.Dispatcher
import com.example.skindiseaseapp.data.di.SkinAppDispatchers
import com.example.skindiseaseapp.data.remote.BodyPartsProvider.getBodyPartsList
import com.example.skindiseaseapp.data.remote.BottomSheetProvider.getBottomSheetList
import com.example.skindiseaseapp.data.repositories.ISkinAppRepository
import com.example.skindiseaseapp.domain.model.body.BodyParts
import com.example.skindiseaseapp.domain.wrapper.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SkinAppRepositoryImpl @Inject constructor(
    @Dispatcher(SkinAppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : ISkinAppRepository {

    override suspend fun getBodyParts(): Flow<Resource<List<BodyParts>>> {
        return flow {
            emit(Resource.Loading)
            try {
//                delay(500)
                val bodyPartsList = getBodyPartsList().mapNotNull {
                    (it as? Resource.Success)?.data
                }
                emit(Resource.Success(bodyPartsList))
            } catch (exception: Exception) {
                emit(Resource.Error("Failed to load body parts ${exception.toString()}"))
            }
        }.catch { exception ->
            emit(Resource.Error("An unexpected error occurred ${exception.toString()}"))
        }
    }

    override suspend fun getBottomSheetForScanLesion() = withContext(ioDispatcher) { getBottomSheetList() }

}
