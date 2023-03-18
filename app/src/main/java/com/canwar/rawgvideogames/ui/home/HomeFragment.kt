package com.canwar.rawgvideogames.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.canwar.rawgvideogames.api.Game
import com.canwar.rawgvideogames.databinding.FragmentHomeBinding
import com.canwar.rawgvideogames.ui.GameAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val gameAdapter by lazy { GameAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val homeViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HomeViewModel::class.java]

        val layoutManager = LinearLayoutManager(this.context)
        binding.rvGame.layoutManager = layoutManager

        homeViewModel.games.observe(viewLifecycleOwner) { games ->
            setRecyclerView(games)
        }

        return binding.root
    }

    private fun setRecyclerView(games : List<Game>) {
        gameAdapter.differ.submitList(games)
        binding.rvGame.adapter = gameAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}