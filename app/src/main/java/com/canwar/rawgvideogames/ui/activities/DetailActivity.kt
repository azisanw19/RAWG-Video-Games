package com.canwar.rawgvideogames.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.canwar.rawgvideogames.R
import com.canwar.rawgvideogames.data.responsemodel.Game
import com.canwar.rawgvideogames.databinding.ActivityDetailBinding
import com.canwar.rawgvideogames.viewmodel.DetailViewModel
import com.canwar.rawgvideogames.viewmodel.ViewModelFactory
import com.canwar.rawgvideogames.ui.fragments.HomeFragment.Companion.EXTRA_GAME_HOME_FRAGMENT

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel: DetailViewModel by viewModels {
        ViewModelFactory(this)
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

        // TODO: Settings menu favorite
        val itemIsSave = menu?.findItem(R.id.detail_save_navigation_menu)
        itemIsSave?.isVisible = false
//        itemIsSave?.icon?

        return super.onCreateOptionsMenu(menu)
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