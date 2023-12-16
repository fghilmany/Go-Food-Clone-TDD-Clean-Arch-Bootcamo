package com.fghilmany.register.http.usecase

import app.cash.turbine.test
import com.fghilmany.common.ConnectivityException
import com.fghilmany.common.DataResult
import com.fghilmany.common.HttpClientResult
import com.fghilmany.common.InvalidDataException
import com.fghilmany.register.domain.RegisterBody
import com.fghilmany.register.http.RegisterHttpClient
import com.fghilmany.register.http.RemoteRegisterBody
import com.fghilmany.register.http.RemoteRegisterResponse
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
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

    @Test
    fun testLoadRequestsData() = runBlocking{
        val remoteBody = RemoteRegisterBody(
            "123",
            "123",
            "Bandung",
            "082134",
            "Bandung",
            "Acuy",
            "17",
            "acuy@email.com",
        )
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
            client.register(remoteBody)
        } returns flowOf()

        sut.register(body).test{
            awaitComplete()
        }

        verify(exactly = 1) {
            client.register(remoteBody)
        }

        confirmVerified(client)
    }
    @Test
    fun testLoadTwiceRequestsDataTwice() = runBlocking{
        val remoteBody = RemoteRegisterBody(
            "123",
            "123",
            "Bandung",
            "082134",
            "Bandung",
            "Acuy",
            "17",
            "acuy@email.com",
        )
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
            client.register(remoteBody)
        } returns flowOf()

        sut.register(body).test{
            awaitComplete()
        }
        sut.register(body).test{
            awaitComplete()
        }

        verify(exactly = 2) {
            client.register(remoteBody)
        }

        confirmVerified(client)
    }

    @Test
    fun testLoadDeliversInvalidDataError() = runBlocking {
        expect(
            sut = sut,
            receivedHttpClientResult = HttpClientResult.Failure(InvalidDataException()),
            expectedResult = "Invalid Data",
            exactly = 1
        )
    }
    @Test
    fun testLoadDeliversConnectivityError() = runBlocking {
        expect(
            sut = sut,
            receivedHttpClientResult = HttpClientResult.Failure(ConnectivityException()),
            expectedResult = "Connectivity",
            exactly = 1
        )
    }

    private fun expect(
        sut: RemoteRegisterInsert,
        receivedHttpClientResult: HttpClientResult<RemoteRegisterResponse>,
        expectedResult: Any,
        exactly: Int = -1,
    ) = runBlocking {

        val remoteBody = RemoteRegisterBody(
            "123",
            "123",
            "Bandung",
            "082134",
            "Bandung",
            "Acuy",
            "17",
            "acuy@email.com",
        )
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
            client.register(remoteBody)
        } returns flowOf(receivedHttpClientResult)

        sut.register(body).test {
            when(val receivedResult = awaitItem()){
                is DataResult.Success -> {
                    //Todo
                }
                is DataResult.Failure -> {
                    assertEquals(expectedResult, receivedResult.errorMessage)
                }
            }
            awaitComplete()
        }

        verify(exactly = exactly) {
            client.register(remoteBody)
        }

        confirmVerified(client)

    }
}