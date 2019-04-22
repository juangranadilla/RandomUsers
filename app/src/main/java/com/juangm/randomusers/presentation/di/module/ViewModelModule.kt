package com.juangm.randomusers.presentation.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.juangm.randomusers.presentation.di.ViewModelFactory
import com.juangm.randomusers.presentation.di.ViewModelKey
import com.juangm.randomusers.presentation.ui.users.UserListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UserListViewModel::class)
    internal abstract fun userListViewModel(viewModel: UserListViewModel): ViewModel
}