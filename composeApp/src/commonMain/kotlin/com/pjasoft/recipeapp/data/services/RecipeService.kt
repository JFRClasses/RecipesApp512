package com.pjasoft.recipeapp.data.services

import com.pjasoft.recipeapp.domain.dtos.Prompt
import com.pjasoft.recipeapp.domain.dtos.RecipeDTO
import com.pjasoft.recipeapp.domain.models.Recipe
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Query

interface RecipeService {
    @POST("recipes/ai-generate")
    suspend fun generateRecipe(@Body prompt: Prompt ) : RecipeDTO

    @GET("recipes")
    suspend fun getRecipesByUserId(@Query("userId") userId:Int) : List<Recipe>

    @POST("recipes")
    suspend fun saveGeneratedRecipe(@Body recipe: Recipe) : Recipe
}