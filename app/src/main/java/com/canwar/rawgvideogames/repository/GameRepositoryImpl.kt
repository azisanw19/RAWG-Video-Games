package com.canwar.rawgvideogames.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.canwar.rawgvideogames.network.api.ApiService
import com.canwar.rawgvideogames.data.responsemodel.Game
import com.canwar.rawgvideogames.paging.GamePagingSource
import com.canwar.rawgvideogames.network.api.NetworkCall
import com.canwar.rawgvideogames.network.api.Resource

class GameRepositoryImpl(private val apiService: ApiService) : GameRepository {

    override fun getGame(searchQuery: String?): LiveData<PagingData<Game>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                GamePagingSource(apiService, searchQuery)
            }
        ).liveData
    }

    override fun detailGame(idGame: Int): LiveData<Resource<Game>> {
        val detailGameCall = NetworkCall<Game>()
        return detailGameCall.makeCall(apiService.detailGame(idGame))
    }

}