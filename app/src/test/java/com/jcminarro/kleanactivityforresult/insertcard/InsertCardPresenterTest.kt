package com.jcminarro.kleanactivityforresult.insertcard

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import org.amshove.kluent.*
import org.junit.Test

class InsertCardPresenterTest{

    private val cardScanner: CardScanner = mock()
    private val insertCardPresenter: InsertCardPresenter = InsertCardPresenter(cardScanner)
    private val view: InsertCardPresenter.View = spy()

    @Test
    fun `Should render card data`() {
        val card = Card("123456789", 9, 1989, "123")
        When calling cardScanner.scan(any()) `it answers`  { (it.arguments[0] as CardScanner.Callback).onScanned(card)}

        insertCardPresenter.view = view
        insertCardPresenter.onScanCard()

        Verify on view that view.showCardNumber(card.number) was called
        Verify on view that view.showCVV(card.cvv) was called
        Verify on view that view.showExpireDate("${card.expirationMonth}/${card.expirationYear}") was called
    }
}