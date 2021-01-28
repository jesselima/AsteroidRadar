package com.udacity.asteroidradar.features.detail.di

import com.udacity.asteroidradar.features.detail.data.datasource.local.AsteroidDetailsLocalDataSource
import com.udacity.asteroidradar.features.detail.data.datasource.local.AsteroidDetailsLocalDataSourceImpl
import com.udacity.asteroidradar.features.detail.data.repository.AsteroidDetailsLocalRepositoryImpl
import com.udacity.asteroidradar.features.detail.domain.reposirory.AsteroidDetailsLocalRepository
import com.udacity.asteroidradar.features.detail.domain.usecase.AsteroidDetailsLocalUseCase
import com.udacity.asteroidradar.features.detail.domain.usecase.AsteroidDetailsLocalUseCaseImpl
import com.udacity.asteroidradar.features.detail.presentation.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object AsteroidDetailsModule {

    private val asteroidsModule = module {

        factory<AsteroidDetailsLocalDataSource> {
            AsteroidDetailsLocalDataSourceImpl(
                application = get()
            )
        }

        factory<AsteroidDetailsLocalRepository> {
            AsteroidDetailsLocalRepositoryImpl(
                asteroidDetailsLocalDataSource = get()
            )
        }

        factory<AsteroidDetailsLocalUseCase> {
            AsteroidDetailsLocalUseCaseImpl(
                asteroidDetailsLocalRepository = get()
            )
        }

        viewModel {
            DetailsViewModel(
                asteroidDetailsLocalUseCase = get(),
             )
        }

    }

    fun loadModuleDependency() = asteroidsModule
}