package com.fghilmany.register.domain

data class RegisterBody(
	val password: String,
	val passwordConfirmation: String,
	val address: String,
	val phoneNumber: String,
	val city: String,
	val name: String,
	val houseNumber: String,
	val email: String
)

