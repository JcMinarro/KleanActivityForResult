package com.jcminarro.kleanactivityforresult

import android.app.Application
import com.jcminarro.kleanactivityforresult.di.component.AppComponent
import com.jcminarro.kleanactivityforresult.di.component.DaggerAppComponent
import com.jcminarro.kleanactivityforresult.di.module.AppModule

class KleanApplication: Application() {
    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    fun getAppComponent(): AppComponent = component
}
