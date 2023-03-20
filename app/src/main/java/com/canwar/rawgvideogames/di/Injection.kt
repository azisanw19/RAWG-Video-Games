package com.canwar.rawgvideogames.di

import android.content.Context
import com.canwar.rawgvideogames.network.api.ApiConfig
import com.canwar.rawgvideogames.repository.GameRepositoryImpl

object Injection {

    fun provideRepository(context: Context): GameRepositoryImpl {
        val apiService = ApiConfig.getApiService()
        return GameRepositoryImpl(apiService)
    }

}