package com.fghilmany.register.http

import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {

    @POST("register")
    suspend fun register(
        @Body resisterBody: RemoteRegisterBody
    ): RemoteRegisterResponse
}