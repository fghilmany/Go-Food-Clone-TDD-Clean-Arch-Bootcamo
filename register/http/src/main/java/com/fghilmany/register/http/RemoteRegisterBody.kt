package com.fghilmany.register.http

import com.squareup.moshi.Json

data class RemoteRegisterBody(

	@Json(name="password")
	val password: String? = null,

	@Json(name="password_confirmation")
	val passwordConfirmation: String? = null,

	@Json(name="address")
	val address: String? = null,

	@Json(name="phoneNumber")
	val phoneNumber: String? = null,

	@Json(name="city")
	val city: String? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="houseNumber")
	val houseNumber: String? = null,

	@Json(name="email")
	val email: String? = null
)
