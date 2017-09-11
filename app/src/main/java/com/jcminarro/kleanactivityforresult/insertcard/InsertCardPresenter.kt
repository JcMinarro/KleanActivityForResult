package com.jcminarro.kleanactivityforresult.insertcard

import javax.inject.Inject

class InsertCardPresenter @Inject constructor(private val cardScanner: CardScanner) {

    internal var view: View? = null

    fun onScanCard() {
        cardScanner.scan(object: CardScanner.Callback {
            override fun onScanned(card: Card) {
                renderCard(card)
            }
        })
    }

    private fun renderCard(card: Card) {
        view?.apply {
            showCardNumber(card.number)
            showExpireDate("${card.expirationMonth}/${card.expirationYear}")
            showCVV(card.cvv)
        }
    }

    interface View {
        fun showCardNumber(cardNumber: String)
        fun showExpireDate(expireDate: String)
        fun showCVV(cvv: String)
    }
}
