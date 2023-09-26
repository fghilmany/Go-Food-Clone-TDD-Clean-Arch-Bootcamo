package com.fghilmany.preference.cache.usecase

import com.fghilmany.preference.cache.LocalPreferenceClient
import com.fghilmany.preference.domain.PreferenceLoader


class LocalPreferenceLoader constructor(private val localLoginClient: LocalPreferenceClient):
    PreferenceLoader {
    override fun loadPreferenceState(): Boolean {
        return localLoginClient.getPreferenceState()
    }
}