package com.jcminarro.kleanactivityforresult.di.module

import com.jcminarro.kleanactivityforresult.insertcard.CardScanner

import dagger.Module
import dagger.Provides

@Module
class CardModule {

    @Provides
    internal fun provideCardScanner(): CardScanner {
        return object: CardScanner {
            override fun scan(callback: CardScanner.Callback) {
                TODO("not implemented")
            }

        }
    }
}
