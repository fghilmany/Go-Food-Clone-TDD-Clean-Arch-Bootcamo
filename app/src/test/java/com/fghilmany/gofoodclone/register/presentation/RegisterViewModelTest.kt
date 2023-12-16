package com.fghilmany.gofoodclone.register.presentation

import androidx.annotation.VisibleForTesting
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.fghilmany.common.DataResult
import com.fghilmany.register.domain.RegisterBody
import com.fghilmany.register.domain.RegisterData
import com.fghilmany.register.domain.RegisterInsert
import com.fghilmany.register.domain.RegisterUser
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
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


class RegisterViewModelTest{
    private val useCase = spyk<RegisterInsert>()
    private lateinit var sut: RegisterViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

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
    @Test
    fun testTwiceRegisterData() = runBlocking {
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
        sut.register()

        verify(exactly = 2) {
            useCase.register(body)
        }

        confirmVerified(useCase)
    }

    @Test
    fun testLoadFailedConnectivity() = runBlocking {
        expect(
            result = DataResult.Failure("Connectivity"),
            sut = sut,
            expectedResult = "Connectivity"
        )
    }

    @Test
    fun testLoadFailedInvalidDataErrorShowsError() = runBlocking {
        expect(
            result = DataResult.Failure("Invalid Data"),
            sut = sut,
            expectedResult = "Invalid Data"
        )
    }
    @Test
    fun testLoadFailedNotFoundErrorShowsError() = runBlocking {
        expect(
            result = DataResult.Failure("Not Found"),
            sut = sut,
            expectedResult = "Not Found"
        )
    }
    @Test
    fun testLoadFailedInternalServerErrorShowsError() = runBlocking {
        expect(
            result = DataResult.Failure("Internal Server Error"),
            sut = sut,
            expectedResult = "Internal Server Error"
        )
    }

    @Test
    fun testLoadFailedUnexpectedErrorShowsError() = runBlocking {
        expect(
            result = DataResult.Failure("Something went wrong"),
            sut = sut,
            expectedResult = "Something went wrong"
        )
    }

    @Test
    fun testLoadSuccessRegister() = runBlocking {
        val data = RegisterData(
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
            result = DataResult.Success(data),
            sut = sut,
            expectedResult = DataResult.Success(data)
        )
    }

    private fun expect(
        result: DataResult<RegisterData>,
        sut: RegisterViewModel,
        expectedResult: Any
    ) {
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
        } returns flowOf(result)

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

        when(val receivedValue = sut.register.getOrAwaitValue()){
            is DataResult.Success -> {
                assertEquals(expectedResult::class.java, receivedValue::class.java)
            }
            is DataResult.Failure -> {
                assertEquals(expectedResult, receivedValue.errorMessage)
            }
        }

        verify(exactly = 1) {
            useCase.register(body)
        }

        confirmVerified(useCase)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS,
        afterObserve: () -> Unit = {}
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }
        this.observeForever(observer)

        try {
            afterObserve.invoke()

            // Don't wait indefinitely if the LiveData is not set.
            if (!latch.await(time, timeUnit)) {
                throw TimeoutException("LiveData value was never set.")
            }

        } finally {
            this.removeObserver(observer)
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }

    suspend fun <T> LiveData<T>.observeForTesting(block: suspend  () -> Unit) {
        val observer = Observer<T> { }
        try {
            observeForever(observer)
            block()
        } finally {
            removeObserver(observer)
        }
    }

}