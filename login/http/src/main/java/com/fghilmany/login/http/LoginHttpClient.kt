package com.fghilmany.login.http

import com.fghilmany.common.HttpClientResult
import kotlinx.coroutines.flow.Flow

class InvalidDataException : Throwable()
class ConnectivityException : Throwable()
class NotFoundExceptionException : Throwable()
class InternalServerErrorException : Throwable()

interface LoginHttpClient {
    fun login(
        body: RemoteLoginBody
    ): Flow<HttpClientResult<RemoteLoginResponse>>
}