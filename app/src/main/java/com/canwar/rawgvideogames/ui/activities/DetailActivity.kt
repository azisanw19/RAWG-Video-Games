package com.canwar.rawgvideogames.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.canwar.rawgvideogames.R
import com.canwar.rawgvideogames.data.responsemodel.Game
import com.canwar.rawgvideogames.databinding.ActivityDetailBinding
import com.canwar.rawgvideogames.viewmodel.DetailViewModel
import com.canwar.rawgvideogames.viewmodel.ViewModelFactory
import com.canwar.rawgvideogames.ui.fragments.HomeFragment.Companion.EXTRA_GAME_HOME_FRAGMENT
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel: DetailViewModel by viewModels {
        ViewModelFactory(this)
    }

    private companion object {
        const val TAG = "DETAIL_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idGame = intent.getIntExtra(EXTRA_GAME_HOME_FRAGMENT, 0)
        detailViewModel.getGame(idGame)

        detailViewModel.games.observe(this) {
            setContent(it)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)


        val deleteFavoriteItem = menu?.findItem(R.id.detail_favorite_navigation_menu)
        val favoriteItem = menu?.findItem(R.id.detail_not_favorite_navigation_menu)

        detailViewModel.isFavorite.observe(this) {
            Log.d(TAG, "isFavorite: $it")
            setFavoriteButton(it, favoriteItem, deleteFavoriteItem)
        }

        return super.onCreateOptionsMenu(menu)
    }

    private fun setFavoriteButton(isFavorite: Boolean, favoriteItem: MenuItem?, deleteFavoriteItem: MenuItem?) {
        if (isFavorite) {
            favoriteItem?.isVisible = false
            deleteFavoriteItem?.isVisible = true
        } else {
            favoriteItem?.isVisible = true
            deleteFavoriteItem?.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.detail_favorite_navigation_menu -> {
                // Delete favorite
                detailViewModel.deleteFavoriteGame()
                Log.d(TAG, "Click Delete Favorite")
            }
            R.id.detail_not_favorite_navigation_menu -> {
                // save favorite
                detailViewModel.saveFavoriteGame()
                Log.d(TAG, "Click favorite")
            }
            else -> super.onOptionsItemSelected(item)
        }

        return true
    }

    private fun setContent(game: Game) {
        Glide.with(this)
            .load(game.backgroundImage)
            .into(binding.ivGameDetails)
        binding.tvTitleDetail.text = game.title
        binding.tvReleaseDateDetails.text = "Release date ${game.released}"
        binding.tvRattingDetails.text = game.rating.toString()
        binding.tvDescriptionDetail.text = Html.fromHtml(game.description, Html.FROM_HTML_MODE_COMPACT)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}