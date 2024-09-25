package com.example.skindiseaseapp.data.remote

import com.example.skindiseaseapp.domain.model.body.BodyParts
import com.example.skindiseaseapp.domain.model.body.BodyType
import com.example.skindiseaseapp.domain.model.bottom_sheet.OnBoardingDataClass
import com.example.skindiseaseapp.domain.wrapper.Resource
import com.example.skindiseaseapp.navigation.utils.SkinDiseaseAppIcons
import com.example.skindiseaseapp.navigation.utils.SkinDiseaseAppIcons.Info

object BottomSheetProvider {
    fun getBottomSheetList(): List<OnBoardingDataClass> {
        return listOf(
            OnBoardingDataClass("Hold you phone perpendicular to the mole", SkinDiseaseAppIcons.Person),
            OnBoardingDataClass("Have only skin be visible in the square view", SkinDiseaseAppIcons.Person),
            OnBoardingDataClass("Focus the camera clearly on the mole", SkinDiseaseAppIcons.Person),

        )
    }
}
