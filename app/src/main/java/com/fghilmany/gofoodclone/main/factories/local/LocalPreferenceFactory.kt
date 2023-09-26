package com.fghilmany.gofoodclone.main.factories.local

import com.fghilmany.gofoodclone.framework.LocalFactory
import com.fghilmany.preference.cache.LocalPreference
import com.fghilmany.preference.cache.LocalPreferenceClient

class LocalPreferenceFactory {
    companion object{
        fun createLocalPreference(): LocalPreferenceClient {
            return LocalPreference(
                LocalFactory.createPreference()
            )
        }
    }
}