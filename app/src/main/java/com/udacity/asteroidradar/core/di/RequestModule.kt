package com.udacity.asteroidradar.core.di

import com.udacity.asteroidradar.core.api.RequestExecutor
import com.udacity.asteroidradar.core.api.RequestExecutorImpl
import org.koin.dsl.module


object RequestModule {

    private val safeRequestModule = module {
        single<RequestExecutor> { RequestExecutorImpl() }
    }

    fun loadModuleDependency() = safeRequestModule
}