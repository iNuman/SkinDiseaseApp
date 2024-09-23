package com.example.skindiseaseapp.ui.splash

sealed class SplashScreenState {
    data object StartFlow : SplashScreenState()
    data object UserSignedIn : SplashScreenState()
    data object UserNotSigned : SplashScreenState()
}
