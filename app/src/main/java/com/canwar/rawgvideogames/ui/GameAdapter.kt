package com.canwar.rawgvideogames.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.canwar.rawgvideogames.api.Game
import com.canwar.rawgvideogames.databinding.ItemRowGameBinding

class GameAdapter : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("OnCreate", "onCreate")
        val binding = ItemRowGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(var binding: ItemRowGameBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setData(game: Game) {
            binding.apply {
                tvTitle.text = game.title
                tvReleaseDate.text = game.released
                tvRating.text = game.rating.toString()
                Glide.with(this.root)
                    .load(game.backgroundImage)
                    .into(ivGame)
            }
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.id == newItem.id
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}