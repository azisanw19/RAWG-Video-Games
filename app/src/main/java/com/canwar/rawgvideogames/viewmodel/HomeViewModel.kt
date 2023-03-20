package com.canwar.rawgvideogames.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.canwar.rawgvideogames.data.responsemodel.Game
import com.canwar.rawgvideogames.repository.GameRepositoryImpl

class HomeViewModel(private val gameRepository: GameRepositoryImpl) : ViewModel() {

    private val searchChange = MutableLiveData<String>()

    val games: LiveData<PagingData<Game>> = searchChange.switchMap {
        searchQuery -> gameRepository.getGame(searchQuery).cachedIn(viewModelScope)
    }

    fun searchChange(searchQuery: String?) {
        searchChange.value = searchQuery ?: ""
    }

    init {
        searchChange(null)
    }

}