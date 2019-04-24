package com.juangm.randomusers.domain.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.juangm.randomusers.domain.models.User

class UsersDataSourceFactory(private val dataSource: UsersDataSource): DataSource.Factory<Int, User>() {

    private val usersLiveData = MutableLiveData<UsersDataSource>()

    override fun create(): DataSource<Int, User> {
        usersLiveData.postValue(dataSource)
        return dataSource
    }
}