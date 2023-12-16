package com.fghilmany.register.http

import app.cash.turbine.test
import com.fghilmany.common.ConnectivityException
import com.fghilmany.common.HttpClientResult
import com.fghilmany.common.NotFoundExceptionException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class RegisterRetrofitClientTest{

    private val service = mockk<RegisterService>()
    private lateinit var sut: RegisterRetrofitClient

    @Before
    fun setUp(){
        sut = RegisterRetrofitClient(service)
    }

    @Test
    fun testGetFailsOnConnectivityError() = runBlocking {
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
        coEvery {
            service.register(body)
        } throws IOException()
        sut.register(body).test {
            when(val receivedResult = awaitItem()){
                is HttpClientResult.Success -> {
                    // TODO
                }
                is HttpClientResult.Failure -> {
                    assertEquals(ConnectivityException()::class.java, receivedResult.throwable::class.java)
                }
            }
            awaitComplete()
        }

        coVerify(exactly = 1) {
            service.register(body)
        }
        confirmVerified(service)
    }

    @Test
    fun testGetFailsOn404HttpResponse() = runBlocking{
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
        val response = Response.error<RemoteRegisterResponse>(
            404,
            "".toResponseBody(null)
        )

        coEvery {
            service.register(body)
        } throws HttpException(response)

        sut.register(body).test {
            when(val receivedResult = awaitItem()){
                is HttpClientResult.Success -> {
                    // TODO
                }
                is HttpClientResult.Failure -> {
                    assertEquals(NotFoundExceptionException()::class.java, receivedResult.throwable::class.java)
                }
            }
            awaitComplete()
        }

        coVerify(exactly = 1) {
            service.register(body)
        }
        confirmVerified(service)
    }

}