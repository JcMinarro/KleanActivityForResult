package com.jcminarro.kleanactivityforresult.insertcard

import java.io.Serializable

data class Card(val number: String, val expirationMonth: Int, val expirationYear: Int, val cvv: String) : Serializable
