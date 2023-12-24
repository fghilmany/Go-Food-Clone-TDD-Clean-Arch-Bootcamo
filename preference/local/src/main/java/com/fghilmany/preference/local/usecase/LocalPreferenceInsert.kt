package com.fghilmany.preference.local.usecase

import com.fghilmany.preference.local.LocalPreferenceClient
import com.fghilmany.preference.domain.PreferenceInsert


class LocalPreferenceInsert(private val localPreferenceClient: LocalPreferenceClient):
    PreferenceInsert {
    override fun insertPreferenceState(isLogin: Boolean) {
        localPreferenceClient.savePreferenceState(isLogin)
    }
}