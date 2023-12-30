package com.fghilmany.login.domain

import com.fghilmany.common.DataResult
import kotlinx.coroutines.flow.Flow

interface LoginInsert {
    fun login(loginBody: LoginBody): Flow<DataResult<LoginUser>>
}