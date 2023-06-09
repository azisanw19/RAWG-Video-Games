package com.canwar.rawgvideogames.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.canwar.rawgvideogames.data.responsemodel.Game
import com.canwar.rawgvideogames.databinding.ItemRowGameBinding

class GameAdapter(private val onClick: (Game) -> Unit) : PagingDataAdapter<Game, GameAdapter.ViewHolder>(
    DIFF_CALLBACK
) {

    inner class ViewHolder(var binding: ItemRowGameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(game: Game) {
            binding.apply {
                tvTitle.text = game.title
                tvReleaseDate.text = "Release date ${game.released}"
                tvRating.text = game.rating.toString()
                Glide.with(this.root)
                    .load(game.backgroundImage)
                    .into(ivGame)
                root.setOnClickListener {
                    onClick(game)
                }
            }
        }

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Game>() {
            override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = getItem(position)
        if (game != null) {
            holder.setData(game)
        }
    }
}