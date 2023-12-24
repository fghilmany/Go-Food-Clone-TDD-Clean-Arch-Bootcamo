package com.fghilmany.gofoodclone.main.factories.local

import com.fghilmany.gofoodclone.framework.LocalFactory
import com.fghilmany.preference.local.LocalPreference
import com.fghilmany.preference.local.LocalPreferenceClient

class LocalPreferenceFactory {
    companion object{
        fun createLocalPreference(): LocalPreferenceClient {
            return LocalPreference(
                LocalFactory.createPreference()
            )
        }
    }
}