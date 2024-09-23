package com.example.skindiseaseapp.domain.model.auth

import androidx.compose.runtime.Immutable

@Immutable
data class User(
    val name: String? = null,
    val surname: String? = null,
    val email: String? = null,
    val image: String? = null,
)

fun User.toUserDomain() = User(
    name = name,
    surname = surname,
    email = email,
    image = image
)