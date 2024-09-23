package com.example.skindiseaseapp.domain.model.body

import javax.annotation.concurrent.Immutable

enum class BodyType {
    FullBody,
    UpperBody,
    LowerBody
}
@Immutable
data class BodyParts(
    val name: String,
    val image: Int,
    val type: BodyType = BodyType.FullBody
)
