package com.fghilmany.gofoodclone.main.factories.register

import com.fghilmany.register.domain.RegisterInsert
import com.fghilmany.register.http.usecase.RemoteRegisterInsert

class RegisterRemoteInsertFactory {
    companion object{
        fun createRegisterRemoteInsert(): RegisterInsert {
            return RemoteRegisterInsert(
                RegisterHttpClientFactory.createRegisterHttpClient()
            )
        }
    }
}