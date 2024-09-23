package com.example.skindiseaseapp.ui.screens.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skindiseaseapp.navigation.utils.Routes
import com.example.skindiseaseapp.ui.splash.SplashScreenEvent
import com.example.skindiseaseapp.ui.splash.SplashScreenState
import com.oguzdogdu.walliescompose.data.repository.IUserAuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authenticationRepository: IUserAuthenticationRepository
) : ViewModel() {

    private val _splashState: MutableStateFlow<SplashScreenState> = MutableStateFlow(
        SplashScreenState.StartFlow
    )
    val splashState = _splashState.asStateFlow()

    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    private val _startDestination = mutableStateOf(Routes.Auth.route)
    val startDestination: State<String> = _startDestination


    init {
        viewModelScope.launch {
            delay(1500L)
            _isReady.value = true
        }

        viewModelScope.launch{
            handleUIEvent(SplashScreenEvent.CheckAuthState)
        }

    }


    fun handleUIEvent(event: SplashScreenEvent) {
        when (event) {
            is SplashScreenEvent.CheckAuthState -> checkSignIn()
        }
    }

    private fun checkSignIn() {
        viewModelScope.launch {
            delay(2000)
            val authState = authenticationRepository.isUserAuthenticatedInFirebase().single()
            if (authState) {
                _splashState.update { SplashScreenState.UserSignedIn }
            } else {
                _splashState.update { SplashScreenState.UserNotSigned }
            }
        }
    }
}
