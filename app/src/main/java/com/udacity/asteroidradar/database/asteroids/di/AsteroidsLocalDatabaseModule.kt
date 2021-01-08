package com.udacity.asteroidradar.database.asteroids.di

import org.koin.dsl.module
import com.udacity.asteroidradar.database.asteroids.datasource.AsteroidsLocalDataSource
import com.udacity.asteroidradar.database.asteroids.datasource.AsteroidsLocalDataSourceImpl


object AsteroidsLocalDatabaseModule {

    private val asteroidsLocalDatabaseModuleModule = module {
        single<AsteroidsLocalDataSource> {
            AsteroidsLocalDataSourceImpl(
                application = get()
            )
        }
    }

    fun loadModuleDependency() = asteroidsLocalDatabaseModuleModule
}