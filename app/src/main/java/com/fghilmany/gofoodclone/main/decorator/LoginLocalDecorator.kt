package com.fghilmany.gofoodclone.main.decorator

import com.fghilmany.common.DataResult
import com.fghilmany.login.domain.LoginBody
import com.fghilmany.login.domain.LoginData
import com.fghilmany.login.domain.LoginInsert
import com.fghilmany.preference.domain.PreferenceInsert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginLocalDecorator(
    private val decorator: LoginInsert,
    private val local: PreferenceInsert
): LoginInsert {
    override fun login(loginBody: LoginBody): Flow<DataResult<LoginData>> {
        return flow {
            decorator.login(loginBody).collect{ response ->
                if (response is DataResult.Success){
                    local.insertPreferenceState(true)
                }
                emit(response)
            }
        }
    }
}