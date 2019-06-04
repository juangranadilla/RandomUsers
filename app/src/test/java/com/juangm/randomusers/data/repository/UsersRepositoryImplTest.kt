package com.juangm.randomusers.data.repository

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.juangm.randomusers.BaseTest
import com.juangm.randomusers.data.source.local.UsersLocalSource
import com.juangm.randomusers.data.source.remote.UsersRemoteSource
import com.juangm.randomusers.domain.models.User
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UsersRepositoryImplTest: BaseTest() {

    @Mock
    private lateinit var usersLocalSource: UsersLocalSource

    @Mock
    private lateinit var usersRemoteSource: UsersRemoteSource

    @Mock
    private lateinit var pagedListBuilder: RxPagedListBuilder<Int, User>

    private lateinit var usersRepository: UsersRepositoryImpl

    private lateinit var user: User

    @Before
    fun setup() {
        usersRepository = UsersRepositoryImpl(usersLocalSource, usersRemoteSource, pagedListBuilder)
        user = User("id", "name", "surname", "email", "smallPicture",
            "normalPicture", "largePicture", "phone", "gender", "street",
            "city", "state", "registered")
    }

    @Test
    fun `get users paged list from database successfully`() {
        @Suppress("UNCHECKED_CAST")
        val usersPagedList = mock(PagedList::class.java) as PagedList<User>
        `when`(pagedListBuilder.buildObservable()).thenReturn(Observable.just(usersPagedList))

        val testObserver = usersRepository.getUserList().test()

        verify(pagedListBuilder).buildObservable()
        testObserver.assertComplete()
    }

    @Test
    fun `error getting users paged list from database`() {
        val throwable = Throwable("Error getting users")
        `when`(pagedListBuilder.buildObservable()).thenReturn(Observable.error(throwable))

        val testObserver = usersRepository.getUserList().test()

        verify(pagedListBuilder).buildObservable()
        testObserver.assertError(throwable)
    }

    @Test
    fun `get favorite users list successfully`() {
        val favoriteUsers = mock<List<User>>()
        `when`(usersLocalSource.getFavoriteUsersFromDatabase()).thenReturn(Single.just(favoriteUsers))

        val testObserver = usersRepository.getFavoriteUserList().test()

        verify(usersLocalSource).getFavoriteUsersFromDatabase()
        testObserver.assertComplete()
    }

    @Test
    fun `error getting favorite users list`() {
        val throwable = Throwable("Error getting favourite users list")
        `when`(usersLocalSource.getFavoriteUsersFromDatabase()).thenReturn(Single.error(throwable))

        val testObserver = usersRepository.getFavoriteUserList().test()

        verify(usersLocalSource).getFavoriteUsersFromDatabase()
        testObserver.assertError(throwable)
    }

    @Test
    fun `update user successfully`() {
        `when`(usersLocalSource.updateUser(any())).thenReturn(Completable.complete())

        val testObserver = usersRepository.updateUser(user).test()

        verify(usersLocalSource).updateUser(any())
        testObserver.assertComplete()
    }

    @Test
    fun `error updating user`() {
        val throwable = Throwable("Error updating user")
        `when`(usersLocalSource.updateUser(any())).thenReturn(Completable.error(throwable))

        val testObserver = usersRepository.updateUser(user).test()

        verify(usersLocalSource).updateUser(any())
        testObserver.assertError(throwable)
    }

    @Test
    fun `delete all local users successfully`() {
        `when`(usersLocalSource.deleteLocalUsers()).thenReturn(Completable.complete())

        val testObserver = usersRepository.deleteLocalUsers().test()

        verify(usersLocalSource).deleteLocalUsers()
        testObserver.assertComplete()
    }

    @Test
    fun `error deleting all local users`() {
        val throwable = Throwable("Error deleting local users")
        `when`(usersLocalSource.deleteLocalUsers()).thenReturn(Completable.error(throwable))

        val testObserver = usersRepository.deleteLocalUsers().test()

        verify(usersLocalSource).deleteLocalUsers()
        testObserver.assertError(throwable)
    }
}