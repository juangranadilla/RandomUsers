package com.juangm.randomusers.domain.usecase

import com.juangm.randomusers.BaseTest
import com.juangm.randomusers.data.repository.UsersRepositoryImpl
import com.juangm.randomusers.domain.models.User
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UpdateUserUseCaseTest: BaseTest() {

    @Mock
    private lateinit var usersRepository: UsersRepositoryImpl

    private lateinit var updateUserUseCase: UpdateUserUseCase

    @Before
    fun setup() {
        updateUserUseCase = UpdateUserUseCase(usersRepository)
    }

    @Test
    fun `execute update user use case successfully`() {
        val user = mock<User>()
        `when`(usersRepository.updateUser(user)).thenReturn(Completable.complete())

        val testObserver = updateUserUseCase.useCaseExecution(user).test()

        verify(usersRepository).updateUser(user)
        testObserver.assertComplete()
    }

    @Test
    fun `execute update user use case returning an error`() {
        val throwable = Throwable("Error updating user")
        val user = mock<User>()
        `when`(usersRepository.updateUser(user)).thenReturn(Completable.error(throwable))

        val testObserver = updateUserUseCase.useCaseExecution(user).test()

        verify(usersRepository).updateUser(user)
        testObserver.assertError(throwable)
    }
}