package com.example.fininfocomassignment.utility

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val configuration =  RealmConfiguration.Builder()
            .name("user_data")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .allowWritesOnUiThread(true)
            .build()
        Realm.setDefaultConfiguration(configuration)
    }
}