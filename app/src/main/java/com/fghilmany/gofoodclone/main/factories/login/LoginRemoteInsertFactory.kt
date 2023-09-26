package com.fghilmany.gofoodclone.main.factories.login

import com.fghilmany.login.domain.LoginInsert
import com.fghilmany.login.http.usecase.RemoteLoginInsert

class LoginRemoteInsertFactory {
    companion object{
        fun createLoginRemoteInsert(): LoginInsert {
            return RemoteLoginInsert(
                LoginHttpClientFactory.createLoginHttpClient()
            )
        }
    }
}