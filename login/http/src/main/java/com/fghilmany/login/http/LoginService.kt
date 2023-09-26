package com.fghilmany.login.http

import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("login")
    suspend fun login(
        @Body loginBody: RemoteLoginBody
    ): RemoteLoginResponse
}