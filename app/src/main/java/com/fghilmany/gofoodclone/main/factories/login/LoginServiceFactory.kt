package com.fghilmany.gofoodclone.main.factories.login

import com.fghilmany.gofoodclone.framework.HttpFactory
import com.fghilmany.login.http.LoginService

class LoginServiceFactory {
    companion object{
        fun createLoginService(): LoginService {
            return HttpFactory.createRetrofit(
                HttpFactory.createMoshi(),
                HttpFactory.createOkhttpClient(
                    HttpFactory.createLoggingInterceptor()
                )
            ).create(LoginService::class.java)
        }
    }
}