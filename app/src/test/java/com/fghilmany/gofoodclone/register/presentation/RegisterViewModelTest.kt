package com.fghilmany.gofoodclone.register.presentation

import com.fghilmany.register.domain.RegisterBody
import com.fghilmany.register.domain.RegisterInsert
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


class RegisterViewModelTest{
    private val useCase = spyk<RegisterInsert>()
    private lateinit var sut: RegisterViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp(){
        sut = RegisterViewModel(useCase)

        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown(){
        clearAllMocks()
    }

    @Test
    fun testInitDoesNotRegister() {
        val body = RegisterBody(
            "123",
            "123",
            "Bandung",
            "082134",
            "Bandung",
            "Acuy",
            "17",
            "acuy@email.com",
        )
        verify(exactly = 0) {
            useCase.register(body)
        }

        confirmVerified(useCase)
    }

    @Test
    fun testRegisterData() = runBlocking {
        val body = RegisterBody(
            "123",
            "123",
            "Bandung",
            "082134",
            "Bandung",
            "Acuy",
            "17",
            "acuy@email.com",
        )
        every {
            useCase.register(body)
        } returns flowOf()

        sut.setRegisterBody(
            "123",
            "123",
            "Bandung",
            "082134",
            "Bandung",
            "Acuy",
            "17",
            "acuy@email.com",
        )
        sut.register()

        verify(exactly = 1) {
            useCase.register(body)
        }

        confirmVerified(useCase)
    }

}