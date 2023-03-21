package com.canwar.rawgvideogames.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.canwar.rawgvideogames.data.responsemodel.Game
import com.canwar.rawgvideogames.repository.GameRepositoryImpl

class FavoriteViewModel(private val gameRepository: GameRepositoryImpl) : ViewModel() {

    val gameList: LiveData<PagingData<Game>> = gameRepository.getPagingFavoriteGame().cachedIn(viewModelScope)

    fun gameList(): LiveData<PagingData<Game>> = gameRepository.getPagingFavoriteGame().cachedIn(viewModelScope)

}