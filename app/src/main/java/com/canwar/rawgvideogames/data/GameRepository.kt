package com.canwar.rawgvideogames.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.canwar.rawgvideogames.api.ApiService
import com.canwar.rawgvideogames.api.Game

class GameRepository(private val apiService: ApiService) {

    fun getGame(searchQuery: String? = null): LiveData<PagingData<Game>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                GamePagingSource(apiService, searchQuery)
            }
        ).liveData
    }

}