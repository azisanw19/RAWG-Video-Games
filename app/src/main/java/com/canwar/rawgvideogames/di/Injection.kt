package com.canwar.rawgvideogames.di

import android.content.Context
import com.canwar.rawgvideogames.api.ApiConfig
import com.canwar.rawgvideogames.data.GameRepository

object Injection {

    fun provideRepository(context: Context): GameRepository {
        val apiService = ApiConfig.getApiService()
        return GameRepository(apiService)
    }

}