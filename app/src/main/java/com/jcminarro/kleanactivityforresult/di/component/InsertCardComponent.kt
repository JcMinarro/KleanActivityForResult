package com.jcminarro.kleanactivityforresult.di.component

import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.jcminarro.kleanactivityforresult.di.module.ActivityModule
import com.jcminarro.kleanactivityforresult.di.scope.PerActivity

import com.jcminarro.kleanactivityforresult.insertcard.InsertCardActivity

import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class))
interface InsertCardComponent {

    fun provideAppCompatActivity(): AppCompatActivity

    fun provideFragmentManager(): FragmentManager

    fun injectActivity(insertCardActivity: InsertCardActivity)
}
