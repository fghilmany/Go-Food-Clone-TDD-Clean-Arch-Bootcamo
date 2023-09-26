package com.fghilmany.gofoodclone.main.factories.login

import com.fghilmany.login.domain.LoginInsert
import com.fghilmany.gofoodclone.main.decorator.LoginCacheDecorator
import com.fghilmany.preference.domain.PreferenceInsert

class LoginDecoratorFactory {
    companion object{
        fun createLoginDecorator(
            decorator: LoginInsert,
            cache: PreferenceInsert
        ): LoginInsert {
            return LoginCacheDecorator(
                decorator, cache
            )
        }
    }
}