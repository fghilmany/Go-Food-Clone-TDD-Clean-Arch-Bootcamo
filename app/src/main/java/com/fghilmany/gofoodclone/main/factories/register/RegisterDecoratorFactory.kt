package com.fghilmany.gofoodclone.main.factories.register

import com.fghilmany.register.domain.RegisterInsert
import com.fghilmany.gofoodclone.main.decorator.RegisterCacheDecorator
import com.fghilmany.preference.domain.PreferenceInsert

class RegisterDecoratorFactory {
    companion object{
        fun createRegisterDecorator(
            decorator: RegisterInsert,
            cache: PreferenceInsert
        ): RegisterInsert {
            return RegisterCacheDecorator(
                decorator, cache
            )
        }
    }
}