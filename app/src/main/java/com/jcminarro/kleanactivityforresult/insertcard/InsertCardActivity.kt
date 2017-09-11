package com.jcminarro.kleanactivityforresult.insertcard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.jcminarro.kleanactivityforresult.R
import kotlinx.android.synthetic.main.activity_insert_card.*

class InsertCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_card)
        setSupportActionBar(toolbar)
    }
}
