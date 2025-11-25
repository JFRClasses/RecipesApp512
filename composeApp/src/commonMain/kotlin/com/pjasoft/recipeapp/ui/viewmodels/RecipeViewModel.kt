package com.pjasoft.recipeapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pjasoft.recipeapp.data.KtorfitClient
import com.pjasoft.recipeapp.domain.dtos.Prompt
import com.pjasoft.recipeapp.domain.dtos.RecipeDTO
import com.pjasoft.recipeapp.domain.models.Recipe
import com.pjasoft.recipeapp.domain.utils.Preferences
import kotlinx.coroutines.launch
// APKs / ABB
class RecipeViewModel : ViewModel() {
    val userId = Preferences.getUserId()
    val recipeService = KtorfitClient.createRecipeService()
    var recipes by mutableStateOf<List<Recipe>>(listOf())
    var generatedRecipe by mutableStateOf<RecipeDTO?>(null)
    var showSheet by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    init {
        getRecipes()
    }
    fun showModalFromList(recipe: RecipeDTO){
        generatedRecipe = recipe
        showSheet = true
    }
    fun hideModal(){
        showSheet = false
    }
    fun generateRecipe(prompt: Prompt){
        viewModelScope.launch {
            try{
                isLoading = true
                val result = recipeService.generateRecipe(prompt)
                showSheet = true
                generatedRecipe = result
                println(result.toString())
            }
            catch (e: Exception){
                showSheet = false
                println(e.toString())
            }
            finally {
                isLoading = false
            }
        }
    }
    fun getRecipes(){
        viewModelScope.launch {
            try{
                val result = recipeService.getRecipesByUserId(userId)
                recipes = result.takeLast(5).reversed()
                println(result.toString())
            }
            catch (e: Exception){
                println(e.toString())
            }
        }
    }

    fun saveRecipeInDb(){
        viewModelScope.launch {
            try {
                val recipe = Recipe(
                    id = 0,
                    userId = userId,
                    category = generatedRecipe?.category ?: "",
                    imageUrl = generatedRecipe?.imageUrl,
                    ingredients = generatedRecipe?.ingredients ?: listOf(),
                    instructions = generatedRecipe?.instructions ?: listOf(),
                    minutes = generatedRecipe?.minutes ?: 0,
                    stars = generatedRecipe?.stars ?: 0,
                    title = generatedRecipe?.title ?: ""
                )
                val result = recipeService.saveGeneratedRecipe(
                    recipe = recipe
                )
                println(result.toString())
                getRecipes()
            }
            catch (e: Exception){
                println(e.toString())
            }
        }
    }

}