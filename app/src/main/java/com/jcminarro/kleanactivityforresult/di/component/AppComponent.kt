package com.jcminarro.kleanactivityforresult.di.component

import android.content.Context
import com.jcminarro.kleanactivityforresult.di.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun provideContext(): Context
}