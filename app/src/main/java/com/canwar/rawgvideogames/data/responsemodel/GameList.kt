package com.canwar.rawgvideogames.data.responsemodel

import com.google.gson.annotations.SerializedName

data class GameList(

    @field:SerializedName("results")
    val games: List<Game>,

    @field:SerializedName("next")
    val nextPage: String,

    @field:SerializedName("previous")
    val previousPage: String

)
