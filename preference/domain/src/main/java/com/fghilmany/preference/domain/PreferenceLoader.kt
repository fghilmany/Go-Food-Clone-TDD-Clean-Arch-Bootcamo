package com.fghilmany.preference.domain

interface PreferenceLoader {
    fun loadPreferenceState(): Boolean
}