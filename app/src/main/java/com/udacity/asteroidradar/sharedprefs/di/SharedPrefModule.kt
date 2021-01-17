package com.udacity.asteroidradar.sharedprefs.di

import android.content.Context
import com.udacity.asteroidradar.sharedprefs.SharedPrefStorage
import com.udacity.asteroidradar.sharedprefs.SharedPrefStorageImpl
import org.koin.dsl.module

object SharedPrefModule {

    private val sharedPrefsModule = module {

        fun prefs(context: Context) = context.getSharedPreferences(
            "USER_PREFS",
            Context.MODE_PRIVATE
        )
        single { prefs(get()) }
        single<SharedPrefStorage> {
            SharedPrefStorageImpl(
                prefs = get()
            )
        }
    }

    fun loadModuleDependency() = sharedPrefsModule

}