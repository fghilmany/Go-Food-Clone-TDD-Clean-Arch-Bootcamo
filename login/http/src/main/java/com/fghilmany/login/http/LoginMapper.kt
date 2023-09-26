package com.fghilmany.login.http

import com.fghilmany.login.domain.LoginBody
import com.fghilmany.login.domain.LoginData
import com.fghilmany.login.domain.User

class LoginMapper {
    companion object{
        fun LoginBody.mapToRemoteBody(): RemoteLoginBody {
            return with(this){
                RemoteLoginBody(
                    email = email,
                    password = password
                )
            }
        }

        fun RemoteLoginData.mapToLoginData(): LoginData {
            return LoginData(
                accessToken = accessToken,
                tokenType = tokenType,
                user = with(remoteUser){
                    User(
                        profilePhotoUrl = profilePhotoUrl,
                        address = address,
                        city = city,
                        roles = roles,
                        houseNumber = houseNumber,
                        createdAt = createdAt,
                        emailVerifiedAt = emailVerifiedAt,
                        currentTeamId = currentTeamId,
                        phoneNumber = phoneNumber,
                        updatedAt = updatedAt,
                        name = name,
                        id = id,
                        profilePhotoPath = profilePhotoPath,
                        email = email
                    )
                }
            )
        }
    }
}