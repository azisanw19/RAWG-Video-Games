package com.canwar.rawgvideogames.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.canwar.rawgvideogames.data.responsemodel.Game

@Database(
    entities = [Game::class],
    version = 2,
    exportSchema = true,

)
abstract class GameDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        @Volatile
        private var INSTANCE: GameDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): GameDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java, "game_database"
                )
                    .fallbackToDestructiveMigrationFrom()
                    .build()
                    .also { INSTANCE = it }
            }
        }

    }
}