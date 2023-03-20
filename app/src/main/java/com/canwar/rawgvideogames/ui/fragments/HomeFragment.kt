package com.canwar.rawgvideogames.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.canwar.rawgvideogames.viewmodel.ViewModelFactory
import com.canwar.rawgvideogames.databinding.FragmentHomeBinding
import com.canwar.rawgvideogames.ui.adapters.GameAdapter
import com.canwar.rawgvideogames.data.responsemodel.Game
import com.canwar.rawgvideogames.ui.activities.DetailActivity
import com.canwar.rawgvideogames.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    companion object {
        private const val TAG = "HOME_FRAGMENT"
        const val EXTRA_GAME_HOME_FRAGMENT = "EXTRA_GAME_HOME_FRAGMENT"
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory(this.requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.rvGame.layoutManager = LinearLayoutManager(this.context)

        return binding.root
    }

    override fun onResume() {

        onEtSearchChanged()
        setRecyclerViewGame()

        super.onResume()
    }

    private fun onEtSearchChanged() {
        binding.etSearch.doAfterTextChanged {
            homeViewModel.searchChange(it.toString())
        }
    }

    private fun setRecyclerViewGame() {
        val adapter = GameAdapter{
            moveIntent(it)
        }
        binding.rvGame.adapter = adapter
        homeViewModel.games.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
            Log.d(TAG, "Observe")

            adapter.addLoadStateListener { combinedLoadState ->
                if (combinedLoadState.refresh is LoadState.Loading) {
                    showLoading(true)
                    showEmpty(false)
                } else if (adapter.itemCount == 0) {
                    showLoading(false)
                    showEmpty(true)
                } else {
                    showLoading(false)
                    showEmpty(false)
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
        intent.putExtra(EXTRA_GAME_HOME_FRAGMENT, game.id)
        startActivity(intent)
    }

}