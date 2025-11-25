package com.pjasoft.recipeapp.data

import com.pjasoft.recipeapp.data.services.AuthService
import com.pjasoft.recipeapp.data.services.RecipeService
import com.pjasoft.recipeapp.data.services.createAuthService
import com.pjasoft.recipeapp.data.services.createRecipeService
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

//HTTP CLIENT 200
object KtorfitClient{
    val httpClient = HttpClient {
        expectSuccess = false
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
        install(HttpTimeout){
            requestTimeoutMillis = 40000
            socketTimeoutMillis = 40000
            connectTimeoutMillis = 40000
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
    val baseUrl = "https://recipes.pjasoft.com/api/"
    private val ktorfit = Ktorfit
        .Builder()
        .baseUrl(baseUrl)
        .httpClient(httpClient)
        .build()

    fun createAuthService() : AuthService{
        return ktorfit.createAuthService()
    }

    fun createRecipeService() : RecipeService{
        return ktorfit.createRecipeService()
    }

}