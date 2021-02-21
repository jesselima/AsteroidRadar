package com.udacity.asteroidradar.features.picturedetails.di

import com.udacity.asteroidradar.features.picturedetails.presentation.PictureOfTheDayViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by jesselima on 17/02/21.
 * This is a part of the project Asteroid Radar.
 */
object PictureOfTheDayDetails {
	private val pictureOfTheDayDetailsModule = module {
		viewModel {
			PictureOfTheDayViewModel(
				pictureOfTheDayUseCase = get(),
			)
		}
	}
	fun loadModuleDependency() = pictureOfTheDayDetailsModule
}