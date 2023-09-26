package com.fghilmany.gofoodclone.framework

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

object LocalFactory {
    lateinit var application: Application

    fun createPreference(): SharedPreferences = application.getSharedPreferences("auth_preferences", Context.MODE_PRIVATE)

}