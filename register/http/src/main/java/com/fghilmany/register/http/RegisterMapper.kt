package com.fghilmany.register.http

import com.fghilmany.register.domain.RegisterBody
import com.fghilmany.register.domain.RegisterUser

class RegisterMapper {
    companion object{
        fun RegisterBody.mapToRemoteBody(): RemoteRegisterBody {
            return with(this){
                RemoteRegisterBody(
                    password = password,
                    passwordConfirmation = passwordConfirmation,
                    address = address,
                    phoneNumber = phoneNumber,
                    city = city,
                    name = name,
                    houseNumber = houseNumber,
                    email = email
                )
            }
        }

        fun RemoteRegisterData.mapToRegisterData(): RegisterUser {
            return with(user) {
                RegisterUser(
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