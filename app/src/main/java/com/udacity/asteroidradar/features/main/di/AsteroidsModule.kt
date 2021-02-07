package com.udacity.asteroidradar.features.main.di

import com.udacity.asteroidradar.features.main.data.datasource.local.AsteroidsFeedLocalDataSource
import com.udacity.asteroidradar.features.main.data.datasource.local.AsteroidsFeedLocalDataSourceImpl
import com.udacity.asteroidradar.features.main.data.datasource.local.PictureOfTheDayLocalDataSource
import com.udacity.asteroidradar.features.main.data.datasource.local.PictureOfTheDayLocalDataSourceImpl
import com.udacity.asteroidradar.features.main.data.datasource.remote.AsteroidsFeedRemoteDataSource
import com.udacity.asteroidradar.features.main.data.datasource.remote.AsteroidsFeedRemoteDataSourceImpl
import com.udacity.asteroidradar.features.main.data.datasource.remote.PictureOfTheDayRemoteDataSource
import com.udacity.asteroidradar.features.main.data.datasource.remote.PictureOfTheDayRemoteDataSourceImpl
import com.udacity.asteroidradar.features.main.data.repository.AsteroidsFeedRepositoryImpl
import com.udacity.asteroidradar.features.main.data.repository.PictureOfTheDayRepositoryImpl
import com.udacity.asteroidradar.features.main.domain.reposirory.AsteroidsFeedRepository
import com.udacity.asteroidradar.features.main.domain.reposirory.PictureOfTheDayRepository
import com.udacity.asteroidradar.features.main.domain.usecase.AsteroidsFeedUseCase
import com.udacity.asteroidradar.features.main.domain.usecase.AsteroidsFeedUseCaseImpl
import com.udacity.asteroidradar.features.main.domain.usecase.PictureOfTheDayUseCase
import com.udacity.asteroidradar.features.main.domain.usecase.PictureOfTheDayUseCaseImpl
import com.udacity.asteroidradar.features.main.presentation.MainViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object AsteroidsModule {

    private val asteroidsModule = module {

        factory<AsteroidsFeedLocalDataSource> {
            AsteroidsFeedLocalDataSourceImpl(
                application = get()
            )
        }

        factory<PictureOfTheDayLocalDataSource> {
            PictureOfTheDayLocalDataSourceImpl(
                    application = get()
            )
        }

        factory<AsteroidsFeedRemoteDataSource> {
            AsteroidsFeedRemoteDataSourceImpl(
                requestExecutor = get(),
                dispatcher = Dispatchers.IO
            )
        }

        factory<PictureOfTheDayRemoteDataSource> {
            PictureOfTheDayRemoteDataSourceImpl(
                requestExecutor = get(),
                dispatcher = Dispatchers.IO
            )
        }

        factory<AsteroidsFeedRepository> {
            AsteroidsFeedRepositoryImpl(
                asteroidsFeedLocalDataSource = get(),
                asteroidsFeedRemoteDataSource = get()
            )
        }

        factory<PictureOfTheDayRepository> {
            PictureOfTheDayRepositoryImpl(
                pictureOfTheDayLocalDataSource = get(),
                pictureOfTheDayRemoteDataSource = get()
            )
        }

        factory<AsteroidsFeedUseCase> {
            AsteroidsFeedUseCaseImpl(
                repository = get()
            )
        }

        factory<PictureOfTheDayUseCase> {
            PictureOfTheDayUseCaseImpl(
                repository = get()
            )
        }

        viewModel {
            MainViewModel(
                asteroidsFeedUseCase = get(),
                pictureOfTheDayUseCase = get(),
                sharedPrefStorage = get(),
                connectionChecker = get()
            )
        }

    }

    fun loadModuleDependency() = asteroidsModule
}