package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.features.main.domain.entities.AsteroidsFeedItem
import com.udacity.asteroidradar.features.main.data.datasource.local.dao.AsteroidsDao
import com.udacity.asteroidradar.features.main.data.datasource.local.dao.PictureOfTheDayDao
import com.udacity.asteroidradar.features.main.domain.entities.PictureOfDay

@Database(
    entities = [
        AsteroidsFeedItem::class,
        PictureOfDay::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun asteroidsDao(): AsteroidsDao
    abstract fun pictureOfTheDay(): PictureOfTheDayDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "asteroid_radar"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}
