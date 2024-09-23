package com.example.skindiseaseapp.navigation.utils

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes(val route : String) {
    @Serializable
    data object Auth : Routes(route = "auth")
    @Serializable
    data object Home : Routes(route = "home")
}