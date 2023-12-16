package com.fghilmany.register.http

import com.fghilmany.common.ConnectivityException
import com.fghilmany.common.HttpClientResult
import com.fghilmany.common.InternalServerErrorException
import com.fghilmany.common.InvalidDataException
import com.fghilmany.common.NotFoundExceptionException
import com.fghilmany.common.UnexpectedException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RegisterRetrofitClient constructor(
    private val loginService: RegisterService
): RegisterHttpClient {
    override fun register(body: RemoteRegisterBody): Flow<HttpClientResult<RemoteRegisterResponse>> {
        return flow {
            try {
                emit(HttpClientResult.Success(loginService.register(body)))
            }catch (throwable: Throwable){
                when(throwable) {
                    is IOException -> {
                        emit(HttpClientResult.Failure(ConnectivityException()))
                    }
                    is HttpException -> {
                        when(throwable.code()){
                            404 -> emit(HttpClientResult.Failure(NotFoundExceptionException()))
                            422 -> emit(HttpClientResult.Failure(InvalidDataException()))
                            500 -> emit(HttpClientResult.Failure(InternalServerErrorException()))
                        }
                    }
                    else -> {
                        emit(HttpClientResult.Failure(UnexpectedException()))
                    }
                }
            }
        }
    }
}