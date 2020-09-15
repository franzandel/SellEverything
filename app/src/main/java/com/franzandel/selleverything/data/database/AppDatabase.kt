package com.franzandel.selleverything.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.franzandel.selleverything.newest.Product

/**
 * Created by Franz Andel on 15/09/20.
 * Android Engineer
 */

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java, "sell_everything.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }

    abstract fun cartProductDao(): CartProductDao
}