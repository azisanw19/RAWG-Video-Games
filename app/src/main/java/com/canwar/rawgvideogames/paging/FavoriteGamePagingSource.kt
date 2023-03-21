package com.canwar.rawgvideogames.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.canwar.rawgvideogames.data.responsemodel.Game
import com.canwar.rawgvideogames.database.GameDao

class FavoriteGamePagingSource(private val dao: GameDao) : PagingSource<Int, Game>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 0
        const val TAG = "FavoriteGamePagingSource"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val gameList = dao.getPagedGame(
                limit = params.loadSize,
                offset = page * params.loadSize
            )

            Log.d(TAG, "Data: $gameList")

            return LoadResult.Page(
                data = gameList,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (gameList.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            Log.d(TAG, "Error: $exception")
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Game>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}