package com.fghilmany.preference.local.usecase

import com.fghilmany.preference.local.LocalPreferenceClient
import com.fghilmany.preference.domain.PreferenceLoader


class LocalPreferenceLoader constructor(private val localLoginClient: LocalPreferenceClient):
    PreferenceLoader {
    override fun loadPreferenceState(): Boolean {
        return localLoginClient.getPreferenceState()
    }
}