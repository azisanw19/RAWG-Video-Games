package com.canwar.rawgvideogames.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.canwar.rawgvideogames.data.responsemodel.Game
import com.canwar.rawgvideogames.databinding.FragmentFavoriteBinding
import com.canwar.rawgvideogames.ui.activities.DetailActivity
import com.canwar.rawgvideogames.ui.activities.DetailActivity.Companion.EXTRA_DETAIL_ACTIVITY
import com.canwar.rawgvideogames.ui.adapters.GameAdapter
import com.canwar.rawgvideogames.viewmodel.FavoriteViewModel
import com.canwar.rawgvideogames.viewmodel.ViewModelFactory

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        binding.rvFavorite.layoutManager = LinearLayoutManager(this.context)

        return binding.root
    }

    override fun onResume() {
        setRecyclerViewGameFavorite()
        super.onResume()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun setRecyclerViewGameFavorite() {
        val gameAdapter = GameAdapter() {
            moveIntent(it)
        }
        binding.rvFavorite.adapter = gameAdapter
        favoriteViewModel.gameList().observe(viewLifecycleOwner) {
            gameAdapter.submitData(lifecycle, it)

            gameAdapter.addLoadStateListener { combinedLoadState ->
                if (combinedLoadState.refresh is LoadState.Loading) {
                    showLoading(true)
                    showEmpty(false)
                } else if (gameAdapter.itemCount == 0) {
                    showLoading(false)
                    showEmpty(true)
                } else {
                    showLoading(false)
                    showEmpty(false)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showEmpty(isEmpty: Boolean) {
        if (isEmpty) {
            binding.ivEmptyGame.visibility = View.VISIBLE
        } else {
            binding.ivEmptyGame.visibility = View.GONE
        }
    }

    private fun moveIntent(game: Game) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(EXTRA_DETAIL_ACTIVITY, game.id)
        startActivity(intent)
    }

}