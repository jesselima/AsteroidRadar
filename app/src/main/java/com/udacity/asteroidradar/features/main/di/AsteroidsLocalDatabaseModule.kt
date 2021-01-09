package com.udacity.asteroidradar.features.main.di

import org.koin.dsl.module
import com.udacity.asteroidradar.features.main.data.datasource.local.AsteroidsLocalDataSource
import com.udacity.asteroidradar.features.main.data.datasource.local.AsteroidsLocalDataSourceImpl


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