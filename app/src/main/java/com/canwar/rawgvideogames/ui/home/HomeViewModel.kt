package com.canwar.rawgvideogames.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.canwar.rawgvideogames.api.Game
import com.canwar.rawgvideogames.api.GameResponse
import com.canwar.rawgvideogames.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _games = MutableLiveData<List<Game>>()
    val games: LiveData<List<Game>> = _games

    companion object{
        private const val TAG = "HomeViewModel"
    }

    init {
        getGame()
    }

    private fun getGame() {
        val client = ApiConfig.getApiService().getGames(1 )
        client.enqueue(object : Callback<GameResponse> {
            override fun onResponse(call: Call<GameResponse>, response: Response<GameResponse>) {
                if (response.isSuccessful) {
                    _games.value = response.body()?.games
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GameResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }


}