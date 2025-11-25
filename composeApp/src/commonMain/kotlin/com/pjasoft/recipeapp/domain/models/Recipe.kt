package com.pjasoft.recipeapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val category: String,
    val id: Int,
    val imageUrl: String?,
    val ingredients: List<String>,
    val instructions: List<String>,
    val minutes: Int,
    val stars: Int,
    val title: String,
    val userId: Int
)