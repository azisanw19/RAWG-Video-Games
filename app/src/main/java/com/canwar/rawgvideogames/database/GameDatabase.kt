package com.canwar.rawgvideogames.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.canwar.rawgvideogames.data.responsemodel.Game
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [Game::class],
    version = 2,
    exportSchema = true,

)
abstract class GameDatabase : RoomDatabase() {

    abstract fun GameDao() : GameDao

    companion object {
        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getDatabase(context: Context, applicationScope: CoroutineScope): GameDatabase {
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