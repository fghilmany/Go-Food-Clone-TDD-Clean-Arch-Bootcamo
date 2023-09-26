package com.fghilmany.gofoodclone.main.factories.register

import com.fghilmany.register.http.RegisterHttpClient
import com.fghilmany.register.http.RegisterRetrofitClient

class RegisterHttpClientFactory {
    companion object{
        fun createRegisterHttpClient(): RegisterHttpClient {
            return RegisterRetrofitClient(
                RegisterServiceFactory.createRegisterService()
            )
        }
    }
}