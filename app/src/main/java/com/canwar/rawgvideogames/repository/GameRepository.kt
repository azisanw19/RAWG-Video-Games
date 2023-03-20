package com.canwar.rawgvideogames.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.canwar.rawgvideogames.data.responsemodel.Game
import com.canwar.rawgvideogames.network.api.Resource

interface GameRepository {
    fun getGame(searchQuery: String? = null): LiveData<PagingData<Game>>

    fun detailGame(idGame: Int): LiveData<Resource<Game>>
}