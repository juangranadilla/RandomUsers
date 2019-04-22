package com.juangm.randomusers.presentation.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun applicationContext(application: Application): Context {
        return application
    }
}