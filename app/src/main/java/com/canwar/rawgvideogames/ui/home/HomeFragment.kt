package com.canwar.rawgvideogames.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.canwar.rawgvideogames.ui.ViewModelFactory
import com.canwar.rawgvideogames.databinding.FragmentHomeBinding
import com.canwar.rawgvideogames.adapter.GameAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val gameAdapter by lazy { GameAdapter() }
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory(this.requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.rvGame.layoutManager = LinearLayoutManager(this.context)

        setRecyclerViewGame()

        return binding.root
    }

    private fun setRecyclerViewGame() {
        val adapter = GameAdapter()
        binding.rvGame.adapter = adapter
        homeViewModel.games.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}