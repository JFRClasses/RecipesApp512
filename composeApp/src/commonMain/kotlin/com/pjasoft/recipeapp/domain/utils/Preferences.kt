package com.pjasoft.recipeapp.domain.utils

import com.russhwolf.settings.Settings

object Preferences {
    private val settings = Settings()

    fun saveUserId(userId:Int){
        settings.putInt("userId",userId)
    }
    fun saveIsLogged(isLogged: Boolean){
        settings.putBoolean("isLogged",isLogged)

    }
    fun getIsLogged() : Boolean{
        return settings.getBoolean("isLogged",false)
    }
    fun getUserId() : Int{
        return settings.getInt("userId",0)
    }
    fun clearSettings(){
        settings.clear()
    }
}