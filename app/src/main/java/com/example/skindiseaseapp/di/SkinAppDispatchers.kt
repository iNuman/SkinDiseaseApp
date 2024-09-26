package com.example.skindiseaseapp.data.di

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val skinAppDispatchers: SkinAppDispatchers)

enum class SkinAppDispatchers {
    Default,
    IO,
}
