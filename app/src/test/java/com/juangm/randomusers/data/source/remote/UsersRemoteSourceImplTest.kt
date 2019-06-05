package com.juangm.randomusers.data.source.remote

import com.juangm.randomusers.BaseTest
import com.juangm.randomusers.data.source.remote.dto.*
import com.juangm.randomusers.data.source.remote.retrofit.UsersService
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UsersRemoteSourceImplTest: BaseTest() {

    @Mock
    private lateinit var usersService: UsersService

    private lateinit var usersRemoteSource: UsersRemoteSource

    @Before
    fun setup() {
        usersRemoteSource = UsersRemoteSourceImpl(usersService)
    }

    @Test
    fun `get users from API successfully`() {
        val page = 1
        val nameDto = NameDto("title", "first", "last")
        val locationDto = LocationDto("street", "city", "state", "zip")
        val loginDto = LoginDto("uuid", "username", "password",
            "salt", "md5", "sha1", "sha256")
        val dobDto = DobDto("date", 27)
        val registeredDto = RegisteredDto("date", 26)
        val pictureDto = PictureDto("large", "medium", "thumbnail")
        val user = UserDto("id", nameDto, locationDto, "email", loginDto, dobDto, registeredDto,
            "phone", "cell", pictureDto, "nat")
        val responseDto = mock<ResponseDto> {
            on(it.results).thenReturn(listOf(user))
        }
        `when`(usersService.getUsers(page)).thenReturn(Single.just(responseDto))

        val testObserver = usersRemoteSource.getUsersFromApi(page).test()

        verify(usersService).getUsers(page)
        testObserver.assertComplete()
    }

    @Test
    fun `error getting users from API`() {
        val page = 1
        val throwable = Throwable("Error getting users from API")
        `when`(usersService.getUsers(page)).thenReturn(Single.error(throwable))

        val testObserver = usersRemoteSource.getUsersFromApi(page).test()

        verify(usersService).getUsers(page)
        testObserver.assertError(throwable)
    }
}