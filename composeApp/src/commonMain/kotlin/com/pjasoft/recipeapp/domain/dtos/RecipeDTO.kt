package com.pjasoft.recipeapp.domain.dtos

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDTO(
    val category: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val minutes: Int,
    val prompt: String,
    val stars: Int,
    val title: String,
    val imageUrl : String
)