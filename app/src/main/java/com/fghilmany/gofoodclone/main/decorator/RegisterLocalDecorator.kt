package com.fghilmany.gofoodclone.main.decorator

import com.fghilmany.common.DataResult
import com.fghilmany.preference.domain.PreferenceInsert
import com.fghilmany.register.domain.RegisterBody
import com.fghilmany.register.domain.RegisterData
import com.fghilmany.register.domain.RegisterInsert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterLocalDecorator(
    private val decorator: RegisterInsert,
    private val local: PreferenceInsert
): RegisterInsert {
    override fun register(registerBody: RegisterBody): Flow<DataResult<RegisterData>> {
        return flow {
            decorator.register(registerBody).collect{ response ->
                if (response is DataResult.Success){
                    local.insertPreferenceState(true)
                }
                emit(response)
            }
        }
    }
}