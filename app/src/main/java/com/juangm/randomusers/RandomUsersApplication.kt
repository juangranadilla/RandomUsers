package com.juangm.randomusers

import android.app.Application
import com.juangm.randomusers.presentation.di.dataModule
import com.juangm.randomusers.presentation.di.domainModule
import com.juangm.randomusers.presentation.di.networkModule
import com.juangm.randomusers.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RandomUsersApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RandomUsersApplication)
            modules(listOf(dataModule, domainModule, networkModule, viewModelModule))
        }
    }
}