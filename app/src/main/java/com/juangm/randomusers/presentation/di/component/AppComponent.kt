package com.juangm.randomusers.presentation.di.component

import android.app.Application
import com.juangm.randomusers.RandomUsersApplication
import com.juangm.randomusers.presentation.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ApplicationModule::class,
    FragmentModule::class,
    NetworkModule::class,
    DataModule::class,
    DomainModule::class,
    ViewModelModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(app: RandomUsersApplication)
}