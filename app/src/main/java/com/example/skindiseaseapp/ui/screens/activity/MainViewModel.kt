package com.example.skindiseaseapp.ui.screens.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skindiseaseapp.SkinApp
import com.example.skindiseaseapp.data.repositories.IAppSettingsRepository
import com.oguzdogdu.walliescompose.data.repository.IUserAuthenticationRepository
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStore: IAppSettingsRepository,
    private val application: SkinApp,
    private val authenticationRepository: IUserAuthenticationRepository
) : ViewModel() {

    private val _appPreferencesState = MutableStateFlow(MainScreenState())
    val appPreferencesState = _appPreferencesState.asStateFlow()

    fun handleScreenEvents(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.ThemeChanged -> {
                getThemeValue()
            }
            MainScreenEvent.CheckUserAuthState -> {
                checkUserAuthState()
            }
        }
    }

    private fun getThemeValue() {
        viewModelScope.launch {
            val value = dataStore.getThemeStrings(key = "theme").first()
            _appPreferencesState.update {
                it.copy(themeValues = value)
            }
            application.theme.value = value.toString()
        }
    }

    private fun checkUserAuthState() {
        viewModelScope.launch {
            val state = authenticationRepository.isUserAuthenticatedInFirebase().single()
            _appPreferencesState.update { it.copy(userAuth = state) }
        }
    }

}