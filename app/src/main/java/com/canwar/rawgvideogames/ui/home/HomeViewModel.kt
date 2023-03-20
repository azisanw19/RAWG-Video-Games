package com.canwar.rawgvideogames.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.canwar.rawgvideogames.api.Game
import com.canwar.rawgvideogames.data.GameRepository

class HomeViewModel(private val gameRepository: GameRepository) : ViewModel() {

    val games: LiveData<PagingData<Game>> =
        gameRepository.getGame().cachedIn(viewModelScope)

    fun gameSearch(searchQuery: String?) : LiveData<PagingData<Game>> = gameRepository.getGame(searchQuery).cachedIn(viewModelScope)

}