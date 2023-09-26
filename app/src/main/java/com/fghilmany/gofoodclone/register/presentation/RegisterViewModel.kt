package com.fghilmany.gofoodclone.register.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.fghilmany.common.DataResult
import com.fghilmany.gofoodclone.main.factories.local.LocalPreferenceInsertFactory
import com.fghilmany.gofoodclone.main.factories.register.RegisterDecoratorFactory
import com.fghilmany.gofoodclone.main.factories.register.RegisterRemoteInsertFactory
import com.fghilmany.register.domain.RegisterBody
import com.fghilmany.register.domain.RegisterData
import com.fghilmany.register.domain.RegisterInsert

import kotlinx.coroutines.launch

class RegisterViewModel constructor(
    private val registerInsert: RegisterInsert
): ViewModel() {

    private lateinit var registerBody: RegisterBody

    private var _register: MutableLiveData<DataResult<RegisterData>> = MutableLiveData()
    val register get() = _register

    fun setRegisterBody(
        password: String,
        passwordConfirmation: String,
        address: String,
        phoneNumber: String,
        city: String,
        name: String,
        houseNumber: String,
        email: String
    ){
        registerBody = RegisterBody(
            password, passwordConfirmation, address, phoneNumber, city, name, houseNumber, email
        )
    }

    fun register(){
        viewModelScope.launch {
            registerInsert.register(registerBody).collect{ result ->
                _register.value = result
            }
        }
    }

    companion object{
        val FACTORY: ViewModelProvider.Factory = viewModelFactory{
            initializer {
                RegisterViewModel(
                    RegisterDecoratorFactory.createRegisterDecorator(
                        decorator = RegisterRemoteInsertFactory.createRegisterRemoteInsert(),
                        cache = LocalPreferenceInsertFactory.createLocalPreferenceInsert()
                    )
                )
            }
        }
    }
}