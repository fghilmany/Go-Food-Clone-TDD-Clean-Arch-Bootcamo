package com.fghilmany.gofoodclone.main.factories.register

import com.fghilmany.gofoodclone.framework.HttpFactory
import com.fghilmany.register.http.RegisterService

class RegisterServiceFactory {
    companion object{
        fun createRegisterService(): RegisterService {
            return HttpFactory.createRetrofit(
                HttpFactory.createMoshi(),
                HttpFactory.createOkhttpClient(
                    HttpFactory.createLoggingInterceptor()
                )
            ).create(RegisterService::class.java)
        }
    }
}