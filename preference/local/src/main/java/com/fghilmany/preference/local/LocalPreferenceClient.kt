package com.fghilmany.preference.local

interface LocalPreferenceClient {
    fun savePreferenceState(isPreference: Boolean)
    fun getPreferenceState(): Boolean
}

class LocalPreferenceKey{
    companion object{
        const val LOGIN_STATE = "login_state"
    }
}