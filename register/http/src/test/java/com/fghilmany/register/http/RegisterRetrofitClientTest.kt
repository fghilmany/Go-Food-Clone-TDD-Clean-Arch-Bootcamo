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
        expect(
            sut = sut,
            expectedResult = ConnectivityException()
        )
    }

    @Test
    fun testGetFailsOn404HttpResponse() = runBlocking{
        expect(
            withStatusCode = 404,
            sut = sut,
            expectedResult = NotFoundExceptionException()
        )
    }

    private fun expect(
        withStatusCode: Int? = null,
        sut: RegisterRetrofitClient,
        expectedResult: Any
    ) = runBlocking {
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
        when{
            withStatusCode != null -> {
                val response = Response.error<RemoteRegisterResponse>(
                    withStatusCode,
                    "".toResponseBody(null)
                )

                coEvery {
                    service.register(body)
                } throws HttpException(response)
            }

            expectedResult is ConnectivityException -> {
                coEvery {
                    service.register(body)
                } throws IOException()
            }
        }


        sut.register(body).test {
            when(val receivedResult = awaitItem()){
                is HttpClientResult.Success -> {
                    // TODO
                }
                is HttpClientResult.Failure -> {
                    assertEquals(expectedResult::class.java, receivedResult.throwable::class.java)
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