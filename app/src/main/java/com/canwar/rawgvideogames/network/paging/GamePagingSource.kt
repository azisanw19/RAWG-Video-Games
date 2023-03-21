package com.canwar.rawgvideogames.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.canwar.rawgvideogames.network.api.ApiService
import com.canwar.rawgvideogames.data.responsemodel.Game

class GamePagingSource(private val apiService: ApiService, private val searchQuery: String? = null) : PagingSource<Int, Game>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseBody = apiService.getGames(
                page = page,
                count = params.loadSize,
                search = searchQuery
                )

            val gameList = mutableListOf<Game>()
            val data = responseBody.body()?.games ?: emptyList()
            gameList.addAll(data)

            return LoadResult.Page(
                data = gameList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseBody.body()!!.games.isEmpty()) null else page + 1
            )
        } catch (exception : Exception) {
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