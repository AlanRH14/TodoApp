package com.example.todoapp

import android.app.Application
import com.example.todoapp.di.appModule
import com.example.todoapp.di.databaseModule
import com.example.todoapp.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ToDoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ToDoApplication)
            modules(
                databaseModule,
                repositoryModule,
                appModule
            )
        }
    }
}