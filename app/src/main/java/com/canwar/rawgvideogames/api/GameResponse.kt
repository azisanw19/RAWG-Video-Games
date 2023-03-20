package com.canwar.rawgvideogames.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class GameResponse(

    @field:SerializedName("results")
    val games: List<Game>,

    @field:SerializedName("next")
    val nextPage: String,

    @field:SerializedName("previous")
    val previousPage: String

)

@Parcelize
data class Game(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val title: String,

    @field:SerializedName("released")
    val released: String,

    @field:SerializedName("background_image")
    val backgroundImage: String,

    @field:SerializedName("rating")
    val rating: Double
) : Parcelable
