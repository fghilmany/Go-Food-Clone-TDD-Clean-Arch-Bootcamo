package com.fghilmany.login.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.fghilmany.common.DataResult
import com.fghilmany.login.domain.LoginBody
import com.fghilmany.login.domain.LoginInsert
import com.fghilmany.login.domain.LoginData
import kotlinx.coroutines.launch

class LoginViewModel constructor(
    private val loginInsert: LoginInsert
): ViewModel() {

    private lateinit var loginBody: LoginBody

    private var _login: MutableLiveData<DataResult<LoginData>> = MutableLiveData()
    val login get() = _login

    fun setLoginBody(email: String, password: String){
        loginBody = LoginBody(
            email = email,
            password = password,
        )
    }

    fun login(){
        viewModelScope.launch {
            loginInsert.login(loginBody).collect{ result ->
                _login.value = result
            }
        }
    }
}