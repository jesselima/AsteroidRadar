package com.udacity.asteroidradar.features.picturesfavoritesgallery.di

import com.udacity.asteroidradar.features.picturesfavoritesgallery.PicturesGalleryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by jesselima on 21/02/21.
 * This is a part of the project Asteroid Radar.
 */
object PicturesGalleryModule {

	private val picturesGalleryModule = module {

		viewModel {
			PicturesGalleryViewModel(
				pictureOfTheDayUseCase = get(),
			)
		}
	}

	fun loadModuleDependency() = picturesGalleryModule
}