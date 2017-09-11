package com.jcminarro.kleanactivityforresult.insertcard

interface CardScanner {

    fun scan(callback: Callback)

    interface Callback {
        fun onScanned(card: Card)
    }
}
