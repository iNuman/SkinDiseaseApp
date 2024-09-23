package com.example.skindiseaseapp.ui.screens.skin_check

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.skindiseaseapp.domain.model.body.BodyParts
import com.example.skindiseaseapp.ui.screens.home.SkinCheckScreenRoute
import kotlinx.serialization.Serializable

@Serializable
data object SkinCheckScreenNavigationRoute

fun NavController.navigateSkinChecksScreen(
    navOptions: NavOptions? = null,
) {
    navigate(route = SkinCheckScreenNavigationRoute, navOptions)
}

fun NavGraphBuilder.skinCheckScreen(onClickStartSkinCheck:()->Unit, onBodyPartItemClicked: (BodyParts) -> Unit) {
    composable<SkinCheckScreenNavigationRoute> {
        SkinCheckScreenRoute(
            onBodyPartItemClicked = { bodyPart ->
                onBodyPartItemClicked.invoke(bodyPart)
            },
            onClickStartSkinCheck = {
                onClickStartSkinCheck.invoke()
            })
    }
}