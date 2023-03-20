package com.canwar.rawgvideogames.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.canwar.rawgvideogames.di.Injection

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(Injection.provideRepository(context)) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java))  {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(Injection.provideRepository(context)) as T

        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}