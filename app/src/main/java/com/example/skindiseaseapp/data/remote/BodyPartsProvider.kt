package com.example.skindiseaseapp.data.remote

import com.example.skindiseaseapp.domain.model.body.BodyParts
import com.example.skindiseaseapp.domain.model.body.BodyType
import com.example.skindiseaseapp.domain.wrapper.Resource
import com.example.skindiseaseapp.navigation.utils.SkinDiseaseAppIcons.Info

object BodyPartsProvider {
    fun getBodyPartsList(): List<Resource<BodyParts>> {
        return listOf(
            Resource.Success(BodyParts("Head", Info, BodyType.UpperBody)),
            Resource.Success(BodyParts("Chest", Info, BodyType.UpperBody)),
            Resource.Success(BodyParts("Left Shoulder", Info, BodyType.UpperBody)),
            Resource.Success(BodyParts("Right Shoulder", Info, BodyType.UpperBody)),
            Resource.Success(BodyParts("Left Arm", Info, BodyType.UpperBody)),
            Resource.Success(BodyParts("Right Arm", Info, BodyType.UpperBody)),
            Resource.Success(BodyParts("Abdomen", Info, BodyType.UpperBody)),
            Resource.Success(BodyParts("Pelvis", Info, BodyType.LowerBody)),
            Resource.Success(BodyParts("Gluteus", Info, BodyType.LowerBody)),
            Resource.Success(BodyParts("Left Upper Leg", Info, BodyType.LowerBody)),
            Resource.Success(BodyParts("Right Upper Leg", Info, BodyType.LowerBody)),
            Resource.Success(BodyParts("Left Lower Leg", Info, BodyType.LowerBody)),
            Resource.Success(BodyParts("Right Lower Leg", Info, BodyType.LowerBody)),
            Resource.Success(BodyParts("Left Foot", Info, BodyType.LowerBody)),
            Resource.Success(BodyParts("Right Foot", Info, BodyType.LowerBody)),
            Resource.Success(BodyParts("Upper Back", Info, BodyType.UpperBody)),
            Resource.Success(BodyParts("Lower Back", Info, BodyType.LowerBody))
        )
    }
}
