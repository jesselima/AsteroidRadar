package com.udacity.asteroidradar.features.mainscreen.di

import com.udacity.asteroidradar.features.mainscreen.data.datasource.local.AsteroidsFeedLocalDataSource
import com.udacity.asteroidradar.features.mainscreen.data.datasource.local.AsteroidsFeedLocalDataSourceImpl
import com.udacity.asteroidradar.features.mainscreen.data.datasource.local.PictureOfTheDayLocalDataSource
import com.udacity.asteroidradar.features.mainscreen.data.datasource.local.PictureOfTheDayLocalDataSourceImpl
import com.udacity.asteroidradar.features.mainscreen.data.datasource.remote.AsteroidsFeedRemoteDataSource
import com.udacity.asteroidradar.features.mainscreen.data.datasource.remote.AsteroidsFeedRemoteDataSourceImpl
import com.udacity.asteroidradar.features.mainscreen.data.datasource.remote.PictureOfTheDayRemoteDataSource
import com.udacity.asteroidradar.features.mainscreen.data.datasource.remote.PictureOfTheDayRemoteDataSourceImpl
import com.udacity.asteroidradar.features.mainscreen.data.repository.AsteroidsFeedRepositoryImpl
import com.udacity.asteroidradar.features.mainscreen.data.repository.PictureOfTheDayRepositoryImpl
import com.udacity.asteroidradar.features.mainscreen.domain.reposirory.AsteroidsFeedRepository
import com.udacity.asteroidradar.features.mainscreen.domain.reposirory.PictureOfTheDayRepository
import com.udacity.asteroidradar.features.mainscreen.domain.usecase.AsteroidsFeedUseCase
import com.udacity.asteroidradar.features.mainscreen.domain.usecase.AsteroidsFeedUseCaseImpl
import com.udacity.asteroidradar.features.mainscreen.domain.usecase.PictureOfTheDayUseCase
import com.udacity.asteroidradar.features.mainscreen.domain.usecase.PictureOfTheDayUseCaseImpl
import com.udacity.asteroidradar.features.mainscreen.presentation.MainViewModel
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