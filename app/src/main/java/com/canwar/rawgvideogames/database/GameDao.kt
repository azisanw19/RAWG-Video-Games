package com.canwar.rawgvideogames.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.canwar.rawgvideogames.data.responsemodel.Game

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(vararg game: Game)

    @Delete
    suspend fun deleteGame(vararg game: Game)

}