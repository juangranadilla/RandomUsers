package com.juangm.randomusers.presentation.ui.userdetail

import com.google.common.truth.Truth
import com.juangm.randomusers.BaseTest
import com.juangm.randomusers.domain.repository.UsersRepository
import com.juangm.randomusers.domain.models.User
import com.juangm.randomusers.domain.usecase.UpdateUserUseCase
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
        val updateUserLiveData = userDetailViewModel.favorite.testObserver()
        `when`(usersRepository.updateUser(user)).thenReturn(Completable.complete())

        userDetailViewModel.updateUser(user)

        Truth.assertThat(updateUserLiveData.observedValues.last())
    }

    @Test
    fun `get an error updating user`() {
        val user = mock<User>()
        val updateUserLiveData = userDetailViewModel.favorite.testObserver()
        `when`(usersRepository.updateUser(user)).thenReturn(Completable.error(Throwable("Error updating user")))

        userDetailViewModel.updateUser(user)

        Truth.assertThat(updateUserLiveData.observedValues).isEmpty()
    }
}