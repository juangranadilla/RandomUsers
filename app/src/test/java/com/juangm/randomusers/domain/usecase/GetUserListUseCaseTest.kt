package com.juangm.randomusers.domain.usecase

import androidx.paging.PagedList
import com.juangm.randomusers.BaseTest
import com.juangm.randomusers.data.repository.UsersRepositoryImpl
import com.juangm.randomusers.domain.models.User
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetUserListUseCaseTest: BaseTest() {

    @Mock
    private lateinit var usersRepository: UsersRepositoryImpl

    private lateinit var getUserListUseCase: GetUserListUseCase

    @Before
    fun setup() {
        getUserListUseCase = GetUserListUseCase(usersRepository)
    }

    @Test
    fun `execute get user list use case successfully`() {
        @Suppress("UNCHECKED_CAST")
        val usersPagedList = Mockito.mock(PagedList::class.java) as PagedList<User>
        `when`(usersRepository.getUserList()).thenReturn(Observable.just(usersPagedList))

        val testObserver = getUserListUseCase.useCaseExecution(Unit).test()

        verify(usersRepository).getUserList()
        testObserver.assertComplete()
    }

    @Test
    fun `execute get user list use case returning an error`() {
        val throwable = Throwable("Error getting users list")
        `when`(usersRepository.getUserList()).thenReturn(Observable.error(throwable))

        val testObserver = getUserListUseCase.useCaseExecution(Unit).test()

        verify(usersRepository).getUserList()
        testObserver.assertError(throwable)
    }
}