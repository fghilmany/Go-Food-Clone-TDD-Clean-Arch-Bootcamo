package com.fghilmany.gofoodclone.main.factories.login

import com.fghilmany.login.http.LoginHttpClient
import com.fghilmany.login.http.LoginRetrofitClient

class LoginHttpClientFactory {
    companion object{
        fun createLoginHttpClient(): LoginHttpClient {
            return LoginRetrofitClient(
                LoginServiceFactory.createLoginService()
            )
        }
    }
}