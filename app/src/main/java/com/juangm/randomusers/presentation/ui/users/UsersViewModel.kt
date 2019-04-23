package com.juangm.randomusers.presentation.ui.users

import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.juangm.randomusers.domain.datasource.UsersDataSourceFactory
import com.juangm.randomusers.domain.models.User
import javax.inject.Inject

private const val PAGE_SIZE = 10
private const val INITIAL_LOAD_SIZE_HINT = 20

class UsersViewModel @Inject constructor(dataSourceFactory: UsersDataSourceFactory): ViewModel() {

    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(INITIAL_LOAD_SIZE_HINT)
        .setPageSize(PAGE_SIZE)
        .build()

    val users = LivePagedListBuilder<Int, User>(dataSourceFactory, config).build()
}