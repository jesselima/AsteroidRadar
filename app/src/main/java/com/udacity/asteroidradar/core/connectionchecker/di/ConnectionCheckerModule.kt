package com.udacity.asteroidradar.core.connectionchecker.di

import com.udacity.asteroidradar.core.connectionchecker.ConnectionChecker
import com.udacity.asteroidradar.core.connectionchecker.ConnectionCheckerImpl
import org.koin.dsl.module


object ConnectionCheckerModule {

    private val connectionCheckerModule = module {
        single<ConnectionChecker> {
            ConnectionCheckerImpl(context = get())
        }
    }

    fun loadModuleDependency() = connectionCheckerModule
}