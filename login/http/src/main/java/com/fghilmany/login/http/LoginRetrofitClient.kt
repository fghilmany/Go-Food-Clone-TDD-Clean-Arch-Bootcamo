package com.fghilmany.login.http

import com.fghilmany.common.HttpClientResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class LoginRetrofitClient constructor(
    private val loginService: LoginService
): LoginHttpClient {
    override fun login(body: RemoteLoginBody): Flow<HttpClientResult<RemoteLoginResponse>> {
        return flow {
            try {
                emit(HttpClientResult.Success(loginService.login(body)))
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
                        emit(HttpClientResult.Failure(InvalidDataException()))
                    }
                }
            }
        }
    }
}