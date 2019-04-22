package com.juangm.randomusers.presentation.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.juangm.randomusers.presentation.di.ViewModelFactory
import com.juangm.randomusers.presentation.di.ViewModelKey
import com.juangm.randomusers.presentation.ui.users.UsersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UsersViewModel::class)
    internal abstract fun userListViewModel(viewModel: UsersViewModel): ViewModel
}