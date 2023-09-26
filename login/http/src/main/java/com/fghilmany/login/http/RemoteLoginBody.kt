package com.fghilmany.login.http

import com.squareup.moshi.Json

data class RemoteLoginBody(

	@Json(name="password")
	val password: String? = null,

	@Json(name="email")
	val email: String? = null
)
