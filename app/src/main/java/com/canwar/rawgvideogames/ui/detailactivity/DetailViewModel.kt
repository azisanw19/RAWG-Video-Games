package com.canwar.rawgvideogames.ui.detailactivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.canwar.rawgvideogames.api.ApiConfig
import com.canwar.rawgvideogames.api.Game
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _game = MutableLiveData<Game>()
    val games: LiveData<Game> = _game

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "DetailViewModel"
    }

    fun getGame(idGame: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().detailGame(idGame)
        client.enqueue(object : Callback<Game> {
            override fun onResponse(call: Call<Game>, response: Response<Game>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _game.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Game>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

}