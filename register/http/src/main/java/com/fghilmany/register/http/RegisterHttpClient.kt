package com.fghilmany.register.http

import com.fghilmany.common.HttpClientResult
import kotlinx.coroutines.flow.Flow

interface RegisterHttpClient {
    fun register(
        body: RemoteRegisterBody
    ): Flow<HttpClientResult<RemoteRegisterResponse>>
}