package com.juangm.randomusers.domain.usecase

import com.juangm.randomusers.BaseTest
import com.juangm.randomusers.data.repository.UsersRepositoryImpl
import com.juangm.randomusers.domain.models.User
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetFavoriteUserListUseCaseTest: BaseTest() {

    @Mock
    private lateinit var usersRepository: UsersRepositoryImpl

    private lateinit var getFavoriteUserListUseCase: GetFavoriteUserListUseCase

    @Before
    fun setup() {
        getFavoriteUserListUseCase = GetFavoriteUserListUseCase(usersRepository)
    }

    @Test
    fun `execute get favorite users list use case successfully`() {
        val favoriteUsers = mock<List<User>>()
        Mockito.`when`(usersRepository.getFavoriteUserList()).thenReturn(Single.just(favoriteUsers))

        val testObserver = getFavoriteUserListUseCase.useCaseExecution(Unit).test()

        Mockito.verify(usersRepository).getFavoriteUserList()
        testObserver.assertComplete()
    }

    @Test
    fun `execute get favorite users list use case returning an error`() {
        val throwable = Throwable("Error getting favorite users list")
        Mockito.`when`(usersRepository.getFavoriteUserList()).thenReturn(Single.error(throwable))

        val testObserver = getFavoriteUserListUseCase.useCaseExecution(Unit).test()

        Mockito.verify(usersRepository).getFavoriteUserList()
        testObserver.assertError(throwable)
    }
}