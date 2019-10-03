package com.juangm.randomusers.data.source.local

import com.juangm.randomusers.BaseTest
import com.juangm.randomusers.data.mapper.mapLocalUserToDomain
import com.juangm.randomusers.data.source.local.room.UsersDao
import com.juangm.randomusers.data.source.local.room.UsersDatabase
import com.juangm.randomusers.data.source.local.room.entity.UserEntity
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UsersLocalSourceImplTest: BaseTest() {

    //TODO how to test / mock DataSource.Factory ?

    @Mock
    private lateinit var usersDao: UsersDao

    private lateinit var usersDatabase: UsersDatabase

    private lateinit var usersLocalSource: UsersLocalSource

    private lateinit var userEntity: UserEntity

    @Before
    fun setup() {
        usersDatabase = mock {
            on(it.usersDao()).thenReturn(usersDao)
        }
        usersLocalSource = UsersLocalSourceImpl(usersDatabase)

        userEntity = UserEntity("id", "name", "surname", "email", "smallPicture",
            "normalPicture", "largePicture", "phone", "gender", "street",
            "city", "state", "country", "registered")
    }

    @Test
    fun `get users count for database successfully`() {
        val usersCount = 20
        `when`(usersDatabase.usersDao().getUsersCount()).thenReturn(Single.just(usersCount))

        val testObserver = usersLocalSource.getUsersCountFromDatabase().test()

        verify(usersDatabase.usersDao()).getUsersCount()
        testObserver.assertComplete()
    }

    @Test
    fun `error getting users count for database`() {
        val throwable = Throwable("Error getting users from database")
        `when`(usersDatabase.usersDao().getUsersCount()).thenReturn(Single.error(throwable))

        val testObserver = usersLocalSource.getUsersCountFromDatabase().test()

        verify(usersDatabase.usersDao()).getUsersCount()
        testObserver.assertError(throwable)
    }

    @Test
    fun `get favorite users from database successfully`() {
        val userEntityList = listOf(userEntity)
        val userList = userEntityList.map(mapLocalUserToDomain)
        `when`(usersDatabase.usersDao().getFavoriteUsers()).thenReturn(Single.just(listOf(userEntity)))

        val testObserver = usersLocalSource.getFavoriteUsersFromDatabase().test()

        verify(usersDatabase.usersDao()).getFavoriteUsers()
        testObserver.assertComplete()
        testObserver.assertValue(userList)
    }

    @Test
    fun `error getting favorite users from database`() {
        val throwable = Throwable("Error getting favorite users from database")
        `when`(usersDatabase.usersDao().getFavoriteUsers()).thenReturn(Single.error(throwable))

        val testObserver = usersLocalSource.getFavoriteUsersFromDatabase().test()

        verify(usersDatabase.usersDao()).getFavoriteUsers()
        testObserver.assertError(throwable)
    }

    @Test
    fun `insert users in database successfully`() {
        val usersToInsert = listOf(userEntity)
        `when`(usersDatabase.usersDao().insertUsers(any())).thenReturn(Completable.complete())

        val testObserver = usersLocalSource.saveUsersInDatabase(usersToInsert).test()

        verify(usersDatabase.usersDao()).insertUsers(usersToInsert)
        testObserver.assertComplete()
    }

    @Test
    fun `error inserting users in database`() {
        val usersToInsert = listOf(userEntity)
        val throwable = Throwable("error inserting users in database")
        `when`(usersDatabase.usersDao().insertUsers(any())).thenReturn(Completable.error(throwable))

        val testObserver = usersLocalSource.saveUsersInDatabase(usersToInsert).test()

        verify(usersDatabase.usersDao()).insertUsers(usersToInsert)
        testObserver.assertError(throwable)
    }

    @Test
    fun `update user successfully`() {
        `when`(usersDatabase.usersDao().update(userEntity)).thenReturn(Completable.complete())

        val testObserver = usersLocalSource.updateUser(userEntity).test()

        verify(usersDatabase.usersDao()).update(userEntity)
        testObserver.assertComplete()
    }

    @Test
    fun `error updating user`() {
        `when`(usersDatabase.usersDao().update(userEntity)).thenReturn(Completable.complete())

        val testObserver = usersLocalSource.updateUser(userEntity).test()

        verify(usersDatabase.usersDao()).update(userEntity)
        testObserver.assertComplete()
    }

    @Test
    fun `delete local users successfully`() {
        `when`(usersDatabase.usersDao().deleteAllUsers()).thenReturn(Completable.complete())

        val testObserver = usersLocalSource.deleteLocalUsers().test()

        verify(usersDatabase.usersDao()).deleteAllUsers()
        testObserver.assertComplete()
    }

    @Test
    fun `error deleting local users`() {
        val throwable = Throwable("Error deleting local users")
        `when`(usersDatabase.usersDao().deleteAllUsers()).thenReturn(Completable.error(throwable))

        val testObserver = usersLocalSource.deleteLocalUsers().test()

        verify(usersDatabase.usersDao()).deleteAllUsers()
        testObserver.assertError(throwable)
    }
}