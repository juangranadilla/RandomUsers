package com.juangm.randomusers.presentation.ui.favorites

import com.google.common.truth.Truth
import com.juangm.randomusers.BaseTest
import com.juangm.randomusers.data.repository.UsersRepository
import com.juangm.randomusers.domain.models.User
import com.juangm.randomusers.domain.usecase.GetFavoriteUserListUseCase
import com.juangm.randomusers.utils.testObserver
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteUsersViewModelTest: BaseTest() {

    @Mock
    private lateinit var usersRepository: UsersRepository

    private lateinit var getFavoriteUserListUseCase: GetFavoriteUserListUseCase

    private lateinit var favoriteUsersViewModel: FavoriteUsersViewModel

    @Before
    fun setup() {
        getFavoriteUserListUseCase = GetFavoriteUserListUseCase(usersRepository)
        favoriteUsersViewModel = FavoriteUsersViewModel(getFavoriteUserListUseCase)
    }

    @Test
    fun `get users list successfully`() {
        val favoriteUsers = mock<List<User>>()
        val favoriteUsersLiveData = favoriteUsersViewModel.favoriteUsers.testObserver()
        Mockito.`when`(usersRepository.getFavoriteUserList()).thenReturn(Single.just(favoriteUsers))

        favoriteUsersViewModel.getFavoriteUsers()

        Truth.assertThat(favoriteUsersLiveData.observedValues.last()).isNotEmpty()
    }

    @Test
    fun `get an error getting users list`() {
        val favoriteUsersLiveData = favoriteUsersViewModel.favoriteUsers.testObserver()
        Mockito.`when`(usersRepository.getFavoriteUserList())
            .thenReturn(Single.error(Throwable("Error getting favorite users list")))

        favoriteUsersViewModel.getFavoriteUsers()

        Truth.assertThat(favoriteUsersLiveData.observedValues).isEmpty()
    }
}