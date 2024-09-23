package com.example.skindiseaseapp.ui.screens.activity

sealed interface MainScreenEvent {
    data object ThemeChanged : MainScreenEvent
    data object CheckUserAuthState : MainScreenEvent
}