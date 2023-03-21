package com.canwar.rawgvideogames.viewmodel

import androidx.lifecycle.*
import com.canwar.rawgvideogames.data.responsemodel.Game
import com.canwar.rawgvideogames.repository.GameRepositoryImpl
import com.canwar.rawgvideogames.network.api.Resource
import kotlinx.coroutines.launch

class DetailViewModel(private val gameRepository: GameRepositoryImpl) : ViewModel() {

    private val _game = MutableLiveData<Game>()
    val games: LiveData<Game> = _game

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val callObserver: Observer<Resource<Game>> = Observer { t ->
        processGame(t)

    }

    companion object {
        private const val TAG = "DETAIL_VIEW_MODEL"
    }

    fun getGame(idGame: Int) {
        gameRepository.detailGame(idGame).observeForever { callObserver.onChanged(it) }
    }

    fun saveFavoriteGame() {
        if (_game.value != null) {
            viewModelScope.launch {
                gameRepository.saveGameFavorite(_game.value!!)
                _isFavorite.value = true
            }
        }
    }

    fun deleteFavoriteGame() {
        if (_game.value != null) {
            viewModelScope.launch {
                gameRepository.deleteGame(_game.value!!)
                _isFavorite.value = false
            }
        }
    }

    private fun getFavoriteGameFromDb(game: Game) {
        viewModelScope.launch {
            gameRepository.getGameDb(game.id).observeForever {
                _isFavorite.value = when (it?.id) {
                    game.id ->  true
                    else -> false
                }
            }
        }
    }

    private fun processGame(response: Resource<Game>) {
        when (response.status) {
            Resource.Status.LOADING -> {
                _isLoading.value = true
            }
            Resource.Status.SUCCESS -> {
                _isLoading.value = false
                if (response.data != null) {
                    _game.value = response.data!!
                    getFavoriteGameFromDb(response.data)
                }
            }
            Resource.Status.ERROR -> {
                _isLoading.value = false
            }
        }
    }

}