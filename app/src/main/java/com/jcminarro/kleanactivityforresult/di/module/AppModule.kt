package com.jcminarro.kleanactivityforresult.di.module

import android.content.Context

import com.jcminarro.kleanactivityforresult.KleanApplication

import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: KleanApplication) {

    @Provides
    internal fun provideContext(): Context = application
}
