package com.juangm.randomusers.domain.usecase

import com.juangm.randomusers.BaseTest
import com.juangm.randomusers.data.repository.UsersRepositoryImpl
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DeleteLocalUsersUseCaseTest: BaseTest() {

    @Mock
    private lateinit var usersRepository: UsersRepositoryImpl

    private lateinit var deleteLocalUsersUseCase: DeleteLocalUsersUseCase

    @Before
    fun setup() {
        deleteLocalUsersUseCase = DeleteLocalUsersUseCase(usersRepository)
    }

    @Test
    fun `execute update user use case successfully`() {
        Mockito.`when`(usersRepository.deleteLocalUsers()).thenReturn(Completable.complete())

        val testObserver = deleteLocalUsersUseCase.useCaseExecution(Unit).test()

        verify(usersRepository).deleteLocalUsers()
        testObserver.assertComplete()
    }

    @Test
    fun `execute update user use case returning an error`() {
        val throwable = Throwable("Error deleting local user")
        Mockito.`when`(usersRepository.deleteLocalUsers()).thenReturn(Completable.error(throwable))

        val testObserver = deleteLocalUsersUseCase.useCaseExecution(Unit).test()

        verify(usersRepository).deleteLocalUsers()
        testObserver.assertError(throwable)
    }
}