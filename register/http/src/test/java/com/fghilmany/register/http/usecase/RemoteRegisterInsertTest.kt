package com.fghilmany.register.http.usecase

import app.cash.turbine.test
import com.fghilmany.common.ConnectivityException
import com.fghilmany.common.DataResult
import com.fghilmany.common.HttpClientResult
import com.fghilmany.common.InternalServerErrorException
import com.fghilmany.common.InvalidDataException
import com.fghilmany.common.NotFoundExceptionException
import com.fghilmany.register.domain.RegisterData
import com.fghilmany.register.domain.RegisterUser
import com.fghilmany.register.http.Meta
import com.fghilmany.register.http.RegisterHttpClient
import com.fghilmany.register.http.RemoteRegisterData
import com.fghilmany.register.http.RemoteRegisterResponse
import com.fghilmany.register.http.User
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
        verify(exactly = 0) {
            client.register(remoteBody)
        }

        confirmVerified(client)
    }

    @Test
    fun testLoadRequestsData() = runBlocking{

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
    @Test
    fun testLoadDeliversNotFoundError() = runBlocking {
        expect(
            sut = sut,
            receivedHttpClientResult = HttpClientResult.Failure(NotFoundExceptionException()),
            expectedResult = "Not Found",
            exactly = 1
        )
    }

    @Test
    fun testLoadDeliversInternalServerError() = runBlocking {
        expect(
            sut = sut,
            receivedHttpClientResult = HttpClientResult.Failure(InternalServerErrorException()),
            expectedResult = "Internal Server Error",
            exactly = 1
        )
    }

    @Test
    fun testLoadDeliversItemsOn200HttpResponseWithResponse() {
        val remoteRegisterData = RemoteRegisterData(
            "298|Ml5yvMueZ5f1xeg8C2a3h6Iaw6sBJrwMT0lwrOMa",
            "Bearer",
            User(
                "https://ui-avatars.com/api/?name=acuy&color=7F9CF5&background=EBF4FF",
                "Jalan berkah",
                "Berlin",
                "USER",
                "1",
                1702725827000,
                null,
                null,
                "1",
                1702725827000,
                "acuy",
                133,
                null,
                "hightech@gmaisl.com",
            )
        )

        val remoteMeta = Meta(
            200,
            "success",
            "User Registered",
        )

        val remoteRegisterResponse = RegisterData(
            "298|Ml5yvMueZ5f1xeg8C2a3h6Iaw6sBJrwMT0lwrOMa",
            "Bearer",
            RegisterUser(
                "https://ui-avatars.com/api/?name=acuy&color=7F9CF5&background=EBF4FF",
                "Jalan berkah",
                "Berlin",
                "USER",
                "1",
                1702725827000,
                null,
                null,
                "1",
                1702725827000,
                "acuy",
                133,
                null,
                "hightech@gmaisl.com",
            )
        )

        expect(
            sut = sut,
            receivedHttpClientResult = HttpClientResult.Success(RemoteRegisterResponse(
                remoteRegisterData,
                remoteMeta
            )),
            expectedResult = DataResult.Success(remoteRegisterResponse),
            exactly = 1
        )
    }

    private fun expect(
        sut: RemoteRegisterInsert,
        receivedHttpClientResult: HttpClientResult<RemoteRegisterResponse>,
        expectedResult: Any,
        exactly: Int = -1,
    ) = runBlocking {

        every {
            client.register(remoteBody)
        } returns flowOf(receivedHttpClientResult)

        sut.register(body).test {
            when(val receivedResult = awaitItem()){
                is DataResult.Success -> {
                    assertEquals(expectedResult::class.java, receivedResult::class.java)
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