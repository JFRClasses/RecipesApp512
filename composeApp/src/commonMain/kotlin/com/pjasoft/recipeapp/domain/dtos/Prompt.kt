package com.pjasoft.recipeapp.domain.dtos

import kotlinx.serialization.Serializable

@Serializable
data class Prompt(
    val ingredients : String
)
