package com.juangm.randomusers.presentation.ui.settings

import com.google.common.truth.Truth
import com.juangm.randomusers.BaseTest
import com.juangm.randomusers.data.repository.UsersRepository
import com.juangm.randomusers.domain.usecase.DeleteLocalUsersUseCase
import com.juangm.randomusers.utils.testObserver
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SettingsViewModelTest: BaseTest() {

    @Mock
    private lateinit var usersRepository: UsersRepository

    private lateinit var deleteLocalUsersUseCase: DeleteLocalUsersUseCase

    private lateinit var settingsViewModel: SettingsViewModel

    @Before
    fun setup() {
        deleteLocalUsersUseCase = DeleteLocalUsersUseCase(usersRepository)
        settingsViewModel = SettingsViewModel(deleteLocalUsersUseCase)
    }

    @Test
    fun `update user successfully`() {
        val usersDeletedLiveData = settingsViewModel.usersDeleted.testObserver()
        Mockito.`when`(usersRepository.deleteLocalUsers()).thenReturn(Completable.complete())

        settingsViewModel.deleteLocalUsers()

        Truth.assertThat(usersDeletedLiveData.observedValues.last()).isTrue()
    }

    @Test
    fun `get an error updating user`() {
        val usersDeletedLiveData = settingsViewModel.usersDeleted.testObserver()
        Mockito.`when`(usersRepository.deleteLocalUsers())
            .thenReturn(Completable.error(Throwable("Error deleting users")))

        settingsViewModel.deleteLocalUsers()

        Truth.assertThat(usersDeletedLiveData.observedValues.last()).isFalse()
    }
}