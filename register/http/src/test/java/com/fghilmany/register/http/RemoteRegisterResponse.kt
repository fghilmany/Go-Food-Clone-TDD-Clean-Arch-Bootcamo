package com.fghilmany.register.http

import com.fghilmany.register.domain.RegisterBody
import com.fghilmany.register.http.RemoteRegisterBody

val remoteBody = RemoteRegisterBody(
    "123",
    "123",
    "Bandung",
    "082134",
    "Bandung",
    "Acuy",
    "17",
    "acuy@email.com",
)
val body = RegisterBody(
    "123",
    "123",
    "Bandung",
    "082134",
    "Bandung",
    "Acuy",
    "17",
    "acuy@email.com",
)