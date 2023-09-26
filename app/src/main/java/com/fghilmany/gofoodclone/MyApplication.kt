package com.fghilmany.gofoodclone

import android.app.Application
import com.fghilmany.gofoodclone.framework.LocalFactory

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        LocalFactory.application = this
    }
}