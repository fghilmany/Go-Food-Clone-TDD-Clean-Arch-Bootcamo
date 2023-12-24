package com.fghilmany.gofoodclone.main.factories.local

import com.fghilmany.preference.local.usecase.LocalPreferenceLoader
import com.fghilmany.preference.domain.PreferenceLoader

class LocalPreferenceLoaderFactory {
    companion object{
        fun createLocalPreferenceLoader(): PreferenceLoader {
            return LocalPreferenceLoader(
                LocalPreferenceFactory.createLocalPreference()
            )
        }
    }
}