package com.juangm.randomusers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.juangm.randomusers.utils.RxSchedulerRule
import org.junit.Rule
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

abstract class BaseTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()
}