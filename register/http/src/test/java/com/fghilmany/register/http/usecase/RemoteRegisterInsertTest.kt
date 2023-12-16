package com.fghilmany.register.http.usecase

import com.fghilmany.register.http.RegisterHttpClient
import com.fghilmany.register.http.RemoteRegisterBody
import io.mockk.confirmVerified
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class RemoteRegisterInsertTest{
    private val client = spyk<RegisterHttpClient>()
    private lateinit var sut: RemoteRegisterInsert

    @Before
    fun setUp(){
        sut = RemoteRegisterInsert(client)
    }

    @Test
    fun testInitDoesNotRequestData(){
        val body = RemoteRegisterBody(
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
            client.register(body)
        }

        confirmVerified(client)
    }
}