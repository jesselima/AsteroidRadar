package com.udacity.asteroidradar.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.features.asteroiddetail.data.datasource.local.AsteroidDetailsDao
import com.udacity.asteroidradar.features.mainscreen.data.datasource.local.dao.AsteroidsDao
import com.udacity.asteroidradar.features.mainscreen.data.datasource.local.dao.PictureOfTheDayDao
import com.udacity.asteroidradar.features.mainscreen.domain.entities.AsteroidsFeedItem
import com.udacity.asteroidradar.features.mainscreen.domain.entities.PictureOfDay

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
    abstract fun asteroidDetailsDao(): AsteroidDetailsDao
    abstract fun pictureOfTheDayDao(): PictureOfTheDayDao

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
