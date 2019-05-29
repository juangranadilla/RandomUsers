package com.juangm.randomusers.presentation.ui.users

import androidx.paging.PagedList
import com.google.common.truth.Truth
import com.juangm.randomusers.BaseTest
import com.juangm.randomusers.data.repository.UsersRepository
import com.juangm.randomusers.domain.models.User
import com.juangm.randomusers.domain.usecase.GetUserListUseCase
import com.juangm.randomusers.utils.testObserver
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UsersViewModelTest: BaseTest() {

    @Mock
    private lateinit var usersRepository: UsersRepository

    private lateinit var getUserListUseCase: GetUserListUseCase

    private lateinit var usersViewModel: UsersViewModel

    @Before
    fun setup() {
        getUserListUseCase = GetUserListUseCase(usersRepository)
        usersViewModel = UsersViewModel(getUserListUseCase)
    }

    @Test
    fun `get users list successfully`() {
        @Suppress("UNCHECKED_CAST")
        val usersPagedList = mock(PagedList::class.java) as PagedList<User>
        val usersLiveData = usersViewModel.users.testObserver()
        `when`(usersRepository.getUserList()).thenReturn(Observable.just(usersPagedList))

        usersViewModel.getUsers()

        Truth.assertThat(usersLiveData.observedValues.last()).isNotEmpty()
    }

    @Test
    fun `get an error getting users list`() {
        val usersLiveData = usersViewModel.users.testObserver()
        `when`(usersRepository.getUserList()).thenReturn(Observable.error(Throwable("Error getting user list")))

        usersViewModel.getUsers()

        Truth.assertThat(usersLiveData.observedValues).isEmpty()
    }
}