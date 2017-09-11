package com.jcminarro.kleanactivityforresult.di.module;

import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    AppCompatActivity appCompatActivity;

    public ActivityModule(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    @Provides
    public AppCompatActivity provideAppCompatActivity() {
        return appCompatActivity;
    }

    @Provides
    FragmentManager provideFragmentManager() {
        return appCompatActivity.getSupportFragmentManager();
    }

    @Provides
    Resources provideResources() {
        return appCompatActivity.getResources();
    }
}