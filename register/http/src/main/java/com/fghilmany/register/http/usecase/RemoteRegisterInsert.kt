package com.fghilmany.register.http.usecase

import com.fghilmany.common.ConnectivityException
import com.fghilmany.common.DataResult
import com.fghilmany.common.HttpClientResult
import com.fghilmany.common.InternalServerErrorException
import com.fghilmany.common.InvalidDataException
import com.fghilmany.common.NotFoundExceptionException
import com.fghilmany.common.UnexpectedException
import com.fghilmany.register.domain.RegisterBody
import com.fghilmany.register.domain.RegisterData
import com.fghilmany.register.domain.RegisterInsert
import com.fghilmany.register.http.RegisterHttpClient
import com.fghilmany.register.http.RegisterMapper.Companion.mapToRegisterData
import com.fghilmany.register.http.RegisterMapper.Companion.mapToRemoteBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteRegisterInsert(
    private val registerHttpClient: RegisterHttpClient
): RegisterInsert {
    override fun register(registerBody: RegisterBody): Flow<DataResult<RegisterData>> {
        return flow {
            val body = registerBody.mapToRemoteBody()
            registerHttpClient.register(body).collect{ result ->
                when(result){
                    is HttpClientResult.Success -> {
                        val register = result.root
                        emit(DataResult.Success(register.remoteRegisterData.mapToRegisterData()))
                    }
                    is HttpClientResult.Failure -> {
                        when(result.throwable){
                            is InvalidDataException -> {
                                emit(DataResult.Failure("Invalid Data"))
                            }
                            is ConnectivityException -> {
                                emit(DataResult.Failure("Connectivity"))
                            }
                            is NotFoundExceptionException -> {
                                emit(DataResult.Failure("Not Found"))
                            }
                            is InternalServerErrorException -> {
                                emit(DataResult.Failure("Internal Server Error"))
                            }
                            is UnexpectedException -> {
                                emit(DataResult.Failure("Something went wrong"))
                            }
                        }
                    }
                }
            }
        }
    }
}