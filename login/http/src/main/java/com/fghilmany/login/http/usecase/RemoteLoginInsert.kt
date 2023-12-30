package com.fghilmany.login.http.usecase

import com.fghilmany.common.DataResult
import com.fghilmany.common.HttpClientResult
import com.fghilmany.login.domain.LoginBody
import com.fghilmany.login.domain.LoginInsert
import com.fghilmany.login.domain.LoginUser
import com.fghilmany.login.http.ConnectivityException
import com.fghilmany.login.http.InternalServerErrorException
import com.fghilmany.login.http.InvalidDataException
import com.fghilmany.login.http.LoginHttpClient
import com.fghilmany.login.http.LoginMapper.Companion.mapToLoginData
import com.fghilmany.login.http.LoginMapper.Companion.mapToRemoteBody
import com.fghilmany.login.http.NotFoundExceptionException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteLoginInsert constructor(
    private val loginHttpClient: LoginHttpClient
): LoginInsert {
    override fun login(loginBody: LoginBody): Flow<DataResult<LoginUser>> {
        return flow {
            val body = loginBody.mapToRemoteBody()
            loginHttpClient.login(body).collect{result ->
                when(result){
                    is HttpClientResult.Success -> {
                        val login = result.root
                        emit(DataResult.Success(login.remoteLoginData.mapToLoginData()))
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
                        }
                    }
                }
            }
        }
    }
}
