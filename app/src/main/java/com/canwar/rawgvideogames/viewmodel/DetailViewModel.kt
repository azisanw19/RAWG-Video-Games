package com.canwar.rawgvideogames.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.canwar.rawgvideogames.data.responsemodel.Game
import com.canwar.rawgvideogames.repository.GameRepositoryImpl
import com.canwar.rawgvideogames.network.api.Resource

class DetailViewModel(private val gameRepository: GameRepositoryImpl) : ViewModel() {

    private val _game = MutableLiveData<Game>()
    val games: LiveData<Game> = _game

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val callObserver: Observer<Resource<Game>> = Observer { t -> processGame(t) }

    companion object{
        private const val TAG = "DetailViewModel"
    }

    fun getGame(idGame: Int) {
        gameRepository.detailGame(idGame).observeForever { callObserver.onChanged(it) }
    }

    private fun processGame(response: Resource<Game>) {
        when(response.status) {
            Resource.Status.LOADING -> {
                _isLoading.value = true
            }
            Resource.Status.SUCCESS -> {
                _isLoading.value = false
                if (response.data != null) {
                    _game.value = response.data!!
                }
            }
            Resource.Status.ERROR -> {
                _isLoading.value = false
            }
        }
    }

}