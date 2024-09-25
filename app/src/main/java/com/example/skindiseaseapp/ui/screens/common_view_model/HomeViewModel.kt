package com.example.skindiseaseapp.ui.screens.common_view_model

//import com.example.skindiseaseapp.ui.screens.schedules.notifications.ScheduleNotification
import android.content.Context
import android.graphics.Bitmap
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skindiseaseapp.SkinApp
import com.example.skindiseaseapp.data.repositories.IAppSettingsRepository
import com.example.skindiseaseapp.data.repositories.INotificationsRepository
import com.example.skindiseaseapp.data.repositories.ISkinAppRepository
import com.example.skindiseaseapp.domain.model.body.BodyParts
import com.example.skindiseaseapp.domain.model.body.BodyType
import com.example.skindiseaseapp.domain.model.bottom_sheet.OnBoardingDataClass
import com.example.skindiseaseapp.domain.wrapper.Resource
import com.example.skindiseaseapp.domain.wrapper.onFailure
import com.example.skindiseaseapp.domain.wrapper.onLoading
import com.example.skindiseaseapp.domain.wrapper.onSuccess
import com.example.skindiseaseapp.ui.screens.home.events.BodyPartsScreenEvent
import com.example.skindiseaseapp.ui.screens.home.events.BottomSheetOnBoardingScreenEvent
import com.example.skindiseaseapp.ui.screens.schedules.SchedulesScreenState
import com.oguzdogdu.walliescompose.features.settings.SchedulesScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStore: IAppSettingsRepository,
    private val notificationRepository: INotificationsRepository,
    private val skinAppRepository: ISkinAppRepository,
//    private val scheduleNotification: ScheduleNotification,
    private val application: SkinApp,
) : ViewModel() {

    private val _settingsState = MutableStateFlow(SchedulesScreenState())
    val settingsState = _settingsState.asStateFlow()

    private val _snackBarState = MutableStateFlow(SchedulesScreenState())
    val snackBarState = _snackBarState.asStateFlow()

    private val _bodyPartsState = MutableStateFlow<List<BodyParts>>(emptyList())
    val bodyPartsState: StateFlow<List<BodyParts>> = _bodyPartsState.asStateFlow()

    private val _bottomSheetDataState = MutableStateFlow<List<OnBoardingDataClass>>(emptyList())
    val bottomSheetDataState: StateFlow<List<OnBoardingDataClass>> = _bottomSheetDataState.asStateFlow()


    private val _bitmaps = MutableStateFlow<List<Bitmap>>(emptyList())
    val bitmaps = _bitmaps.asStateFlow()



    fun handleBodyPartEvent(event: BodyPartsScreenEvent) {
        when (event) {
            is BodyPartsScreenEvent.GetFullBody -> getBodyParts(BodyType.FullBody)
            is BodyPartsScreenEvent.GetUpperBody -> getBodyParts(BodyType.UpperBody)
            is BodyPartsScreenEvent.GetLowerBody -> getBodyParts(BodyType.LowerBody)
            else -> getBodyParts()  // Fetch the complete list without filtering
        }
    }

    fun handleBottomSheetEvent(event: BottomSheetOnBoardingScreenEvent) {
        when (event) {
            is BottomSheetOnBoardingScreenEvent.GetScanLesionOItems -> getBottomSheetScanLesionData()
            else -> {}
        }
    }

    private fun getBottomSheetScanLesionData() {
        viewModelScope.launch {
            _bottomSheetDataState.value = skinAppRepository.getBottomSheetForScanLesion()
        }
    }

    private fun getBodyParts(bodyType: BodyType? = null) {
        viewModelScope.launch {
            skinAppRepository.getBodyParts().collect { status ->
                status.onLoading {
                    // Set loading state if needed
//                    _bodyPartsState.value = Resource.Loading
                }
                status.onSuccess { list ->
                    val filteredList = if (bodyType == BodyType.FullBody) {
                        list
                    } else {
                        bodyType?.let {
                            list.filter { it.type == bodyType }
                        } ?: list
                    }

                    _bodyPartsState.value = filteredList
                }
                status.onFailure { error ->
//                    _bodyPartsState.value = error.
                }
            }
        }
    }

    fun onTakePhoto(bitmap: Bitmap) {
        _bitmaps.value += bitmap
    }


    fun handleScreenEvents(event: SchedulesScreenEvent) {
        when (event) {
            is SchedulesScreenEvent.OpenThemeDialog -> {
                openTheme(event.open)
            }

            is SchedulesScreenEvent.SetNewTheme -> setThemeValue(event.value)
            is SchedulesScreenEvent.ThemeChanged -> {
                getThemeValue()
            }

            is SchedulesScreenEvent.ClearCached -> statusClearCache(event.isCleared)
            is SchedulesScreenEvent.ProfileIconInHomeClicked -> statusClearCache(event.isProfileIconInHomeClicked)
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    fun onScheduleButtonClicked(
        context: Context,
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int,
        timePickerState: TimePickerState,
        datePickerState: DatePickerState,
        title: String,
    ) {
//        scheduleNotification.scheduleNotification(
//            year = year,
//            month = month,
//            day = day,
//            hour = hour,
//            minute = minute,
//            timePickerState = timePickerState,
//            datePickerState = datePickerState,
//            title = title
//        )
    }


    private fun statusClearCache(showSnackBar: Boolean?) {
        viewModelScope.launch {
            _snackBarState.update {
                it.copy(showSnackBar = showSnackBar)
            }
        }
    }


    private fun openTheme(open: Boolean) {
        viewModelScope.launch {
            _settingsState.update { it.copy(openThemeDialog = open) }
        }
    }


    private fun setThemeValue(value: String) = runBlocking {
        dataStore.putThemeStrings(key = "theme", value = value)
    }

    private fun getThemeValue() {
        viewModelScope.launch {
            dataStore.getThemeStrings(key = "theme").collect { value ->
                _settingsState.updateAndGet {
                    it.copy(getThemeValue = value)
                }
                if (value != null) {
                    application.theme.value = value
                }
            }
        }
    }

}