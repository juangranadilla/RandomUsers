package com.juangm.randomusers.presentation.di.module

import com.juangm.randomusers.presentation.ui.users.UsersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    internal abstract fun contributeUsersFragment(): UsersFragment
}