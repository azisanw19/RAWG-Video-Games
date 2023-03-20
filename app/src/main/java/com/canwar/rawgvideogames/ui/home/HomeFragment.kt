package com.canwar.rawgvideogames.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.canwar.rawgvideogames.ui.ViewModelFactory
import com.canwar.rawgvideogames.databinding.FragmentHomeBinding
import com.canwar.rawgvideogames.adapter.GameAdapter
import com.canwar.rawgvideogames.api.Game
import com.canwar.rawgvideogames.ui.DetailActivity

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

    private var currentText = ""
    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable {
        Log.d(TAG, currentText)
        setRecyclerViewGame(currentText)
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
        /**
         * Handling Recycler View
         **/
        if (currentText == "") {
            setRecyclerViewGame()
        } else {
            // TODO: Bug setelah orientation berubah currentText berubah "", tidak melanjutkan pencarian
            setRecyclerViewGame(currentText)
        }

        /**
         * Handling Search Edit Text Changed
         * */
        onEtSearchChanged()
        super.onResume()
    }

    private fun onEtSearchChanged() {
        binding.etSearch.doAfterTextChanged {
            currentText = it?.toString() ?: ""

            handler.removeCallbacks(searchRunnable)
            handler.postDelayed(searchRunnable, 500L)

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

    private fun setRecyclerViewGame(searchQuery: String) {
        val adapter = GameAdapter{
            moveIntent(it)
        }
        binding.rvGame.adapter = adapter
        homeViewModel.gameSearch(searchQuery).observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
            Log.d(TAG, "Observe Search Query")

            adapter.addLoadStateListener { loadState ->
                if (loadState.refresh is LoadState.Loading) {
                    showLoading(true)
                    showEmpty(false)
                }
                else if (adapter.itemCount == 0) {
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
        intent.putExtra(EXTRA_GAME_HOME_FRAGMENT, game)
        startActivity(intent)
    }

}