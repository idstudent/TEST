package com.example.joinbtnenabled

import android.app.Application
import com.chibatching.kotpref.Kotpref

class testApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Kotpref.init(this)
    }
}