package com.juangm.randomusers.presentation.ui.favorites

import com.google.common.truth.Truth.assertThat
import com.juangm.randomusers.BaseTest
import com.juangm.randomusers.domain.models.User
import com.juangm.randomusers.domain.repository.UsersRepository
import com.juangm.randomusers.domain.usecase.GetFavoriteUserListUseCase
import com.juangm.randomusers.domain.usecase.UpdateUserUseCase
import com.juangm.randomusers.presentation.ui.common.Event
import com.juangm.randomusers.utils.TestObserver
import com.juangm.randomusers.utils.testObserver
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteUsersViewModelTest: BaseTest() {

    @Mock
    private lateinit var usersRepository: UsersRepository

    private lateinit var getFavoriteUserListUseCase: GetFavoriteUserListUseCase

    private lateinit var updateUserUseCase: UpdateUserUseCase

    private lateinit var favoriteUsersViewModel: FavoriteUsersViewModel

    private lateinit var user: User

    @Before
    fun setup() {
        getFavoriteUserListUseCase = GetFavoriteUserListUseCase(usersRepository)
        updateUserUseCase = UpdateUserUseCase(usersRepository)
        favoriteUsersViewModel = FavoriteUsersViewModel(getFavoriteUserListUseCase, updateUserUseCase)

        user = User("id", "name", "surname", "email", "smallPicture",
            "normalPicture", "largePicture", "phone", "gender", "street",
            "city", "state", "country", "registered", false)
    }

    @Test
    fun `get favorite users list successfully`() {
        val favoriteUsers = mock<List<User>>()
        val favoriteUsersLiveData = favoriteUsersViewModel.favoriteUsers.testObserver()
        `when`(usersRepository.getFavoriteUserList()).thenReturn(Single.just(favoriteUsers))

        favoriteUsersViewModel.getFavoriteUsers()

        verify(usersRepository).getFavoriteUserList()
        assertThat(favoriteUsersLiveData.observedValues.last()).isNotEmpty()
    }

    @Test
    fun `get an error getting favorite users list`() {
        val favoriteUsersLiveData = favoriteUsersViewModel.favoriteUsers.testObserver()
        `when`(usersRepository.getFavoriteUserList())
            .thenReturn(Single.error(Throwable("Error getting favorite users list")))

        favoriteUsersViewModel.getFavoriteUsers()

        verify(usersRepository).getFavoriteUserList()
        assertThat(favoriteUsersLiveData.observedValues).isEmpty()
    }

    @Test
    fun `add user to favorites successfully`() {
        `return success when updating user`()
        val updateUserLiveDataEvent = favoriteUsersViewModel.updateUserEvent.testObserver()

        favoriteUsersViewModel.addUserToFavorites(user)

        `verify favorite user updated successfully`(updateUserLiveDataEvent)
        assertThat(user.favorite).isTrue()
    }

    @Test
    fun `error adding user to favorites`() {
        val updateUserLiveDataEvent = favoriteUsersViewModel.updateUserEvent.testObserver()
        `return error when updating user`()

        favoriteUsersViewModel.addUserToFavorites(user)

        `verify error updating favorite user`(updateUserLiveDataEvent)
        assertThat(user.favorite).isTrue()
    }

    @Test
    fun `remove user from favorites successfully`() {
        `return success when updating user`()
        val updateUserLiveDataEvent = favoriteUsersViewModel.updateUserEvent.testObserver()

        favoriteUsersViewModel.removeUserFromFavorites(user)

        `verify favorite user updated successfully`(updateUserLiveDataEvent)
        assertThat(user.favorite).isFalse()
    }

    @Test
    fun `error removing user from favorites`() {
        val updateUserLiveDataEvent = favoriteUsersViewModel.updateUserEvent.testObserver()
        `return error when updating user`()

        favoriteUsersViewModel.removeUserFromFavorites(user)

        `verify error updating favorite user`(updateUserLiveDataEvent)
        assertThat(user.favorite).isFalse()
    }

    private fun `return success when updating user`() {
        val favoriteUsers = mock<List<User>>()
        `when`(usersRepository.updateUser(user)).thenReturn(Completable.complete())
        `when`(usersRepository.getFavoriteUserList()).thenReturn(Single.just(favoriteUsers))
    }

    private fun `return error when updating user`() {
        `when`(usersRepository.updateUser(user))
            .thenReturn(Completable.error(Throwable()))
    }

    private fun `verify favorite user updated successfully`(updateUserLiveDataEvent: TestObserver<Event<User>>) {
        verify(usersRepository).updateUser(any())
        assertThat(updateUserLiveDataEvent.observedValues.last())
    }

    private fun `verify error updating favorite user`(updateUserLiveDataEvent: TestObserver<Event<User>>) {
        verify(usersRepository).updateUser(any())
        assertThat(updateUserLiveDataEvent.observedValues.isEmpty())
    }
}