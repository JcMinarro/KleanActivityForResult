package com.jcminarro.kleanactivityforresult.insertcard

import javax.inject.Inject

class InsertCardPresenter @Inject constructor() {

    lateinit var view: View

    fun onScanCard() {
        TODO("not implemented")
    }

    interface View {
        fun showCardNumber(cardNumber: String)
        fun showExpireDate(expireDate: String)
        fun showCVV(cvv: String)
    }
}
