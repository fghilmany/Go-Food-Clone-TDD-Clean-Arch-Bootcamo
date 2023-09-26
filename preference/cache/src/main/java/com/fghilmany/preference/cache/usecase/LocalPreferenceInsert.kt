package com.fghilmany.preference.cache.usecase

import com.fghilmany.preference.cache.LocalPreferenceClient
import com.fghilmany.preference.domain.PreferenceInsert


class LocalPreferenceInsert(private val localPreferenceClient: LocalPreferenceClient):
    PreferenceInsert {
    override fun insertPreferenceState(isLogin: Boolean) {
        localPreferenceClient.savePreferenceState(isLogin)
    }
}