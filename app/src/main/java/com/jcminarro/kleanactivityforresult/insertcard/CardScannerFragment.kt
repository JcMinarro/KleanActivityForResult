package com.jcminarro.kleanactivityforresult.insertcard

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.*
import android.support.v4.app.Fragment
import android.support.v4.os.IResultReceiver
import io.card.payment.CardIOActivity
import io.card.payment.CreditCard

class CardScannerFragment : Fragment() {

    private lateinit var identifier: String
    private lateinit var resultReceiver: ResultReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        identifier = arguments.getString(ARG_EXTRA_IDENTIFIER)
        resultReceiver = arguments.getParcelable(ARG_EXTRA_RESULT_RECEIVER)
        if (!isRunningCardScanner(savedInstanceState)) {
            startScan()
        }
    }

    private fun startScan() =
            startActivityForResult(Intent(activity, CardIOActivity::class.java)
                    .apply {
                        putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true)
                        putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true)
                    }, REQUEST_CODE_SCAN_CARD)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(BUNDLE_EXTRA_IS_RUNNING_CARD_SCANNER, true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_SCAN_CARD) {
            if (data?.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT) == true) {
                val scanResult = data.getParcelableExtra<CreditCard>(CardIOActivity.EXTRA_SCAN_RESULT)
                val card = Card(
                        scanResult.cardNumber,
                        scanResult.expiryMonth,
                        scanResult.expiryYear,
                        scanResult.cvv)
                notifyCardScanned(card)
            } else {
                notifyScanCanceled()
            }
        }
    }

    private fun isRunningCardScanner(savedInstanceState: Bundle?): Boolean =
            savedInstanceState?.getBoolean(BUNDLE_EXTRA_IS_RUNNING_CARD_SCANNER, false) ?: false

    private fun notifyCardScanned(card: Card) =
            resultReceiver.send(RESULT_OK,
                    Bundle().apply {
                        putSerializable(RESULT_EXTRA_CARD, card)
                        putString(RESULT_EXTRA_IDENTIFIER, identifier)
                    })

    private fun notifyScanCanceled() =
            resultReceiver.send(RESULT_CANCELED,
                    Bundle().apply { putString(RESULT_EXTRA_IDENTIFIER, identifier) })

    /**
     * This class was copied from [android.support.v4.os.ResultReceiver] to avoid the warning added on the
     * sdk because it started to be Restricted to the library group
     */
    open class ResultReceiver : Parcelable {
        val local: Boolean
        val handler: Handler?

        var receiver: IResultReceiver? = null

        internal inner class MyRunnable(val resultCode: Int, val resultData: Bundle) : Runnable {

            override fun run() {
                onReceiveResult(resultCode, resultData)
            }
        }

        internal inner class MyResultReceiver : IResultReceiver.Stub() {
            override fun send(resultCode: Int, resultData: Bundle) {
                handler?.post(MyRunnable(resultCode, resultData)) ?: onReceiveResult(resultCode, resultData)
            }
        }

        /**
         * Create a new ResultReceive to receive results.  Your
         * [.onReceiveResult] method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         */
        constructor(handler: Handler) {
            local = true
            this.handler = handler
        }

        /**
         * Deliver a result to this receiver.  Will call [.onReceiveResult],
         * always asynchronously if the receiver has supplied a Handler in which
         * to dispatch the result.
         * @param resultCode Arbitrary result code to deliver, as defined by you.
         * @param resultData Any additional data provided by you.
         */
        fun send(resultCode: Int, resultData: Bundle) {
            if (local) {
                handler?.post(MyRunnable(resultCode, resultData)) ?: onReceiveResult(resultCode, resultData)
                return
            }

            if (receiver != null) {
                try {
                    receiver!!.send(resultCode, resultData)
                } catch (e: RemoteException) {
                }

            }
        }

        /**
         * Override to receive results delivered to this object.
         *
         * @param resultCode Arbitrary result code delivered by the sender, as
         * defined by the sender.
         * @param resultData Any additional data provided by the sender.
         */
        protected open fun onReceiveResult(resultCode: Int, resultData: Bundle) {}

        override fun describeContents(): Int {
            return 0
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            synchronized(this) {
                if (receiver == null) {
                    receiver = MyResultReceiver()
                }
                out.writeStrongBinder(receiver!!.asBinder())
            }
        }

        constructor(`in`: Parcel) {
            local = false
            handler = null
            receiver = IResultReceiver.Stub.asInterface(`in`.readStrongBinder())
        }

        companion object {

            val CREATOR: Parcelable.Creator<ResultReceiver> = object : Parcelable.Creator<ResultReceiver> {
                override fun createFromParcel(`in`: Parcel): ResultReceiver {
                    return ResultReceiver(`in`)
                }

                override fun newArray(size: Int): Array<ResultReceiver?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

    companion object {

        private val REQUEST_CODE_SCAN_CARD = 69
        private val ARG_EXTRA_IDENTIFIER = "identifier"
        private val ARG_EXTRA_RESULT_RECEIVER = "resultReceiver"
        private val BUNDLE_EXTRA_IS_RUNNING_CARD_SCANNER = "isRunningCardScanner"
        val RESULT_EXTRA_IDENTIFIER = "identifier"
        val RESULT_EXTRA_CARD = "card"

        fun newInstance(identifier: String, resultReceiver: ResultReceiver): CardScannerFragment =
                CardScannerFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_EXTRA_IDENTIFIER, identifier)
                        putParcelable(ARG_EXTRA_RESULT_RECEIVER, resultReceiver)
                    }
                }
    }
}
