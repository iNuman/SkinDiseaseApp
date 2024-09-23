package com.example.skindiseaseapp.ui.screens.login

import androidx.compose.runtime.Stable

@Stable
sealed class LoginScreenEvent {
    data class GoogleButton(val idToken: String?) : LoginScreenEvent()
}
