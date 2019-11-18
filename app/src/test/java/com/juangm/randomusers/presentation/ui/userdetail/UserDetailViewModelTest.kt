package com.juangm.randomusers.presentation.ui.userdetail

import com.google.common.truth.Truth
import com.juangm.randomusers.BaseTest
import com.juangm.randomusers.domain.models.User
import com.juangm.randomusers.domain.repository.UsersRepository
import com.juangm.randomusers.domain.usecase.UpdateUserUseCase
import com.juangm.randomusers.utils.TestObserver
import com.juangm.randomusers.utils.testObserver
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserDetailViewModelTest: BaseTest() {

    @Mock
    private lateinit var usersRepository: UsersRepository

    private lateinit var updateUserUseCase: UpdateUserUseCase

    private lateinit var userDetailViewModel: UserDetailViewModel

    @Before
    fun setup() {
        updateUserUseCase = UpdateUserUseCase(usersRepository)
        userDetailViewModel = UserDetailViewModel(updateUserUseCase)
    }

    @Test
    fun `update user successfully`() {
        val user = mock<User> {
            on(it.favorite).thenReturn(true)
        }
        val favorite = userDetailViewModel.favorite.testObserver()
        val favoriteUsersButtonEnabled = userDetailViewModel.favoriteUsersButtonEnabled.testObserver()
        `when`(usersRepository.updateUser(user)).thenReturn(Completable.complete())

        userDetailViewModel.updateUser(user)

        Truth.assertThat(favorite.observedValues.last())
        `check favorite user button is disabled while updating user`(favoriteUsersButtonEnabled)
    }

    @Test
    fun `get an error updating user`() {
        val user = mock<User>()
        val favorite = userDetailViewModel.favorite.testObserver()
        val favoriteUsersButtonEnabled = userDetailViewModel.favoriteUsersButtonEnabled.testObserver()
        `when`(usersRepository.updateUser(user)).thenReturn(Completable.error(Throwable("Error updating user")))

        userDetailViewModel.updateUser(user)

        Truth.assertThat(favorite.observedValues).isEmpty()
        `check favorite user button is disabled while updating user`(favoriteUsersButtonEnabled)
    }

    private fun `check favorite user button is disabled while updating user`(favoriteUsersButtonEnabled: TestObserver<Boolean>) {
        Truth.assertThat(favoriteUsersButtonEnabled.observedValues[0]).isTrue()
        Truth.assertThat(favoriteUsersButtonEnabled.observedValues[1]).isFalse()
        Truth.assertThat(favoriteUsersButtonEnabled.observedValues[2]).isTrue()
    }
}