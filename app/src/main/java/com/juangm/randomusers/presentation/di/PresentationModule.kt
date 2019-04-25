package com.juangm.randomusers.presentation.di

import com.juangm.randomusers.presentation.ui.users.UsersViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { UsersViewModel(get()) }
}