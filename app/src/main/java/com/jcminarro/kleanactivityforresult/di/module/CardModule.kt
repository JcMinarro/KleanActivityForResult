package com.jcminarro.kleanactivityforresult.di.module

import com.jcminarro.kleanactivityforresult.insertcard.AndroidCardScanner
import com.jcminarro.kleanactivityforresult.insertcard.CardScanner

import dagger.Module
import dagger.Provides

@Module
class CardModule {

    @Provides
    internal fun provideCardScanner(androidCardScanner: AndroidCardScanner): CardScanner = androidCardScanner
}
