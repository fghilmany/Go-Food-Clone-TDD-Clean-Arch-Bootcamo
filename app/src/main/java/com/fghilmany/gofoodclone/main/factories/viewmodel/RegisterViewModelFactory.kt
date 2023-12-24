package com.fghilmany.gofoodclone.main.factories.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.fghilamny.register.presentation.RegisterViewModel
import com.fghilmany.gofoodclone.main.factories.local.LocalPreferenceInsertFactory
import com.fghilmany.gofoodclone.main.factories.register.RegisterDecoratorFactory
import com.fghilmany.gofoodclone.main.factories.register.RegisterRemoteInsertFactory

class RegisterViewModelFactory {
    companion object{
        fun createRegisterViewModelFactory() : ViewModelProvider.Factory = viewModelFactory{
            initializer {
                RegisterViewModel(
                    RegisterDecoratorFactory.createRegisterDecorator(
                        decorator = RegisterRemoteInsertFactory.createRegisterRemoteInsert(),
                        local = LocalPreferenceInsertFactory.createLocalPreferenceInsert()
                    )
                )
            }
        }
    }
}