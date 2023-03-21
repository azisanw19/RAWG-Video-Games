package com.canwar.rawgvideogames.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.canwar.rawgvideogames.data.responsemodel.Game

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(vararg game: Game)

    @Query("SELECT * FROM favorite_game")
    fun getAllGame(): LiveData<List<Game>>

    @Query("SELECT * FROM favorite_game WHERE id = :id")
    fun getGame(id: Int):LiveData<Game>

    @Delete
    suspend fun deleteGame(vararg game: Game)

}