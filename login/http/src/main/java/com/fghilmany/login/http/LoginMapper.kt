package com.fghilmany.login.http

import com.fghilmany.login.domain.LoginBody
import com.fghilmany.login.domain.LoginUser

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

        fun RemoteLoginData.mapToLoginData(): LoginUser {
            return with(this.remoteUser) {
                LoginUser(
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
        }
    }
}