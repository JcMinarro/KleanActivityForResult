package com.jcminarro.kleanactivityforresult.insertcard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jcminarro.kleanactivityforresult.KleanApplication

import com.jcminarro.kleanactivityforresult.R
import com.jcminarro.kleanactivityforresult.di.component.DaggerInsertCardComponent
import com.jcminarro.kleanactivityforresult.di.module.ActivityModule
import kotlinx.android.synthetic.main.activity_insert_card.*
import kotlinx.android.synthetic.main.content_insert_card.*
import javax.inject.Inject

class InsertCardActivity : AppCompatActivity(), InsertCardPresenter.View {

    @Inject lateinit var insertCardPresenter: InsertCardPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDI()
        setContentView(R.layout.activity_insert_card)
        setSupportActionBar(toolbar)
        scanButton.setOnClickListener { insertCardPresenter.onScanCard() }
        insertCardPresenter.view = this
    }

    private fun initDI() {
        DaggerInsertCardComponent.builder()
                .activityModule(ActivityModule(this))
                .appComponent((application as KleanApplication).getAppComponent())
                .build()
                .injectActivity(this)
    }

    override fun showCardNumber(cardNumber: String) = tvCardNumber.setText(cardNumber)

    override fun showExpireDate(expireDate: String) = tvExpireDate.setText(expireDate)

    override fun showCVV(cvv: String) = tvCvv.setText(cvv)
}
