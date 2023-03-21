package com.canwar.rawgvideogames.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.canwar.rawgvideogames.network.api.ApiService
import com.canwar.rawgvideogames.data.responsemodel.Game
import com.canwar.rawgvideogames.database.GameDatabase
import com.canwar.rawgvideogames.paging.GamePagingSource
import com.canwar.rawgvideogames.network.api.NetworkCall
import com.canwar.rawgvideogames.network.api.Resource
import com.canwar.rawgvideogames.paging.FavoriteGamePagingSource

class GameRepositoryImpl(private val apiService: ApiService, private val database: GameDatabase) : GameRepository {

    private companion object {
        const val TAG = "GAME_REPOSITORY"
    }

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

    override suspend fun saveGameFavorite(game: Game) {
        Log.d(TAG, "Insert Database ${game.id}")
        database.gameDao().insertGame(game)
    }

    override fun getPagingFavoriteGame(): LiveData<PagingData<Game>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                FavoriteGamePagingSource(database.gameDao())
            }
        ).liveData
    }

    override suspend fun deleteGame(game: Game) {
        database.gameDao().deleteGame(game)
    }

    override fun getGameDb(id: Int): LiveData<Game> = database.gameDao().getGame(id)


}