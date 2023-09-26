package com.fghilmany.register.domain

import com.fghilmany.common.DataResult
import kotlinx.coroutines.flow.Flow

interface RegisterInsert {
    fun register(registerBody: RegisterBody): Flow<DataResult<RegisterData>>
}