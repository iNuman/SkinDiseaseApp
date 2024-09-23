package com.example.skindiseaseapp.ui.splash

sealed interface SplashScreenEvent {
    data object CheckAuthState : SplashScreenEvent
}