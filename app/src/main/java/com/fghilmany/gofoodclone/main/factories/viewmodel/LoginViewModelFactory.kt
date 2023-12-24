package com.fghilmany.gofoodclone.main.factories.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.fghilmany.gofoodclone.main.factories.local.LocalPreferenceInsertFactory
import com.fghilmany.gofoodclone.main.factories.login.LoginDecoratorFactory
import com.fghilmany.gofoodclone.main.factories.login.LoginRemoteInsertFactory
import com.fghilmany.login.presentation.LoginViewModel

class LoginViewModelFactory {
    companion object{
        fun createLoginViewModel() : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                LoginViewModel(
                    LoginDecoratorFactory.createLoginDecorator(
                        decorator = LoginRemoteInsertFactory.createLoginRemoteInsert(),
                        local = LocalPreferenceInsertFactory.createLocalPreferenceInsert()
                    )
                )
            }
        }
    }
}