package com.fghilmany.gofoodclone.register.presentation

import com.fghilmany.register.domain.RegisterBody
import com.fghilmany.register.domain.RegisterInsert
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.spyk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test


class RegisterViewModelTest{
    private val useCase = spyk<RegisterInsert>()
    private lateinit var sut: RegisterViewModel

    @Before
    fun setUp(){
        sut = RegisterViewModel(useCase)
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

}