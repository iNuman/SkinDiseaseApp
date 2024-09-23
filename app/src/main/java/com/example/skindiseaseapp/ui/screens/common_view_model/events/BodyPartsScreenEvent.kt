package com.example.skindiseaseapp.ui.screens.home.events

sealed class BodyPartsScreenEvent {
    data object GetFullBody : BodyPartsScreenEvent()
    data object GetUpperBody : BodyPartsScreenEvent()
    data object GetLowerBody : BodyPartsScreenEvent()
}