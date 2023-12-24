package com.fghilmany.preference.local

import android.content.SharedPreferences

class LocalPreference(private val preferences: SharedPreferences): LocalPreferenceClient {

    override fun savePreferenceState(isPreference: Boolean) {
        val edit = preferences.edit()
        edit.putBoolean(LocalPreferenceKey.LOGIN_STATE, isPreference)
        edit.apply()
    }

    override fun getPreferenceState(): Boolean {
        return preferences.getBoolean(LocalPreferenceKey.LOGIN_STATE, false)
    }
}