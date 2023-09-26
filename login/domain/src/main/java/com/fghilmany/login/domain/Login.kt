package com.fghilmany.login.domain

data class Login(
    val loginData: LoginData,
    val meta: Meta
)

data class LoginData(
    val accessToken: String,
    val tokenType: String,
    val user: User
)

data class Meta(
    val code: Int,
    val message: String,
    val status: String
)

data class User(
    val profilePhotoUrl: String,
    val address: String,
    val city: String,
    val roles: String,
    val houseNumber: String,
    val createdAt: Long,
    val emailVerifiedAt: Any? = null,
    val currentTeamId: Any? = null,
    val phoneNumber: String,
    val updatedAt: Long,
    val name: String,
    val id: Int,
    val profilePhotoPath: Any? = null,
    val email: String
)
