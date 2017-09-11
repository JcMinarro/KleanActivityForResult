package com.jcminarro.kleanactivityforresult.insertcard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jcminarro.kleanactivityforresult.KleanApplication

import com.jcminarro.kleanactivityforresult.R
import com.jcminarro.kleanactivityforresult.di.component.DaggerInsertCardComponent
import com.jcminarro.kleanactivityforresult.di.module.ActivityModule
import kotlinx.android.synthetic.main.activity_insert_card.*

class InsertCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDI()
        setContentView(R.layout.activity_insert_card)
        setSupportActionBar(toolbar)
    }

    private fun initDI() {
        DaggerInsertCardComponent.builder()
                .activityModule(ActivityModule(this))
                .appComponent((application as KleanApplication).getAppComponent())
                .build()
                .injectActivity(this)
    }
}
