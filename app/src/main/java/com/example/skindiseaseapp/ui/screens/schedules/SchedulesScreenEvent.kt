package com.oguzdogdu.walliescompose.features.settings

sealed interface SchedulesScreenEvent {
    data class OpenThemeDialog(val open: Boolean = false) : SchedulesScreenEvent
    //    data class OpenLanguageDialog(val open:Boolean = false): SettingsScreenEvent
    data class SetNewTheme(val value: String) : SchedulesScreenEvent
    data object ThemeChanged : SchedulesScreenEvent

    //    data class SetNewLanguage(val value: String) : SettingsScreenEvent
//    data object LanguageChanged: SettingsScreenEvent
    data class ClearCached(val isCleared: Boolean? = null) : SchedulesScreenEvent
    data class ProfileIconInHomeClicked(val isProfileIconInHomeClicked: Boolean = false) : SchedulesScreenEvent
}