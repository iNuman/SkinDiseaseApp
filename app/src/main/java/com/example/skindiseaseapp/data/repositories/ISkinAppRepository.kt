package com.example.skindiseaseapp.data.repositories

import com.example.skindiseaseapp.domain.model.body.BodyParts
import com.example.skindiseaseapp.domain.wrapper.Resource
import kotlinx.coroutines.flow.Flow

interface ISkinAppRepository {
    suspend fun getBodyParts():  Flow<Resource<List<BodyParts>>>
}