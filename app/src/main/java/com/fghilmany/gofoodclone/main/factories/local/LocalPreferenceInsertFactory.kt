package com.fghilmany.gofoodclone.main.factories.local

import com.fghilmany.preference.local.usecase.LocalPreferenceInsert
import com.fghilmany.preference.domain.PreferenceInsert

class LocalPreferenceInsertFactory {
    companion object{
        fun createLocalPreferenceInsert(): PreferenceInsert {
            return LocalPreferenceInsert(
                LocalPreferenceFactory.createLocalPreference()
            )
        }
    }
}