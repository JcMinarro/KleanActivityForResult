package com.jcminarro.kleanactivityforresult.insertcard

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.FragmentManager
import javax.inject.Inject

class AndroidCardScanner @Inject
constructor(private val fragmentManager: FragmentManager) : CardScanner {
    private val resultReceiver: CardScannerFragment.ResultReceiver = createResultReceiver()
    private val callbacksMap: MutableMap<String, CardScanner.Callback> = mutableMapOf()

    override fun scan(callback: CardScanner.Callback) {
        val identifier = System.currentTimeMillis().toString()
        val cardScannerFragment = createFragment(identifier, resultReceiver)
        callbacksMap.put(identifier, callback)
        addFragment(identifier, cardScannerFragment)
    }

    private fun createFragment(identifier: String, resultReceiver: CardScannerFragment.ResultReceiver): CardScannerFragment =
            CardScannerFragment.newInstance(identifier, resultReceiver)

    private fun createResultReceiver(): CardScannerFragment.ResultReceiver =
            object : CardScannerFragment.ResultReceiver(Handler(Looper.getMainLooper())) {
                override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
                    super.onReceiveResult(resultCode, resultData)
                    processResult(resultCode, resultData)
                }
            }

    private fun processResult(resultCode: Int, resultData: Bundle) {
        val fragmentTag = resultData.getString(CardScannerFragment.RESULT_EXTRA_IDENTIFIER)
        if (resultCode == RESULT_OK) {
            val card = resultData.getSerializable(CardScannerFragment.RESULT_EXTRA_CARD) as Card
            onCardScanned(fragmentTag, card)
        }
        removeFragment(fragmentTag)
        removeCallback(fragmentTag)
    }

    private fun addFragment(fragmentTag: String, cardScannerFragment: CardScannerFragment) {
        Handler().post {
            fragmentManager.beginTransaction().apply {
                add(cardScannerFragment, fragmentTag)
                commitAllowingStateLoss()
            }
        }
    }

    private fun removeFragment(fragmentTag: String) {
        if (canRemoveFragment(fragmentTag)) {
            fragmentManager.beginTransaction().apply {
                remove(fragmentManager.findFragmentByTag(fragmentTag))
                commitAllowingStateLoss()
            }
        }
    }

    private fun canRemoveFragment(fragmentTag: String): Boolean =
            fragmentManager.findFragmentByTag(fragmentTag)?.let { isActivityActive(it.activity) } ?: false

    private fun isActivityActive(activity: Activity?): Boolean = when {
        activity == null -> false
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 -> !activity.isDestroyed
        else -> !activity.isFinishing
    }

    private fun onCardScanned(fragmentTag: String, card: Card) =
            removeCallback(fragmentTag)?.onScanned(card)

    private fun removeCallback(identifier: String): CardScanner.Callback? = callbacksMap.remove(identifier)
}
