package com.example.app

import androidx.fragment.app.FragmentActivity
import com.example.app.R
import com.example.app.MyApp.Companion.context
import com.example.app.MyApp.Companion.settingsVm
import com.example.app.ui.snackBar.MyEventType

object Printer {
    interface PrintLabelListener {
        fun printLabel(printThis: String, qty: Int, onFinish: (Boolean) -> Unit)
    }

    object PrinterFactory {
        fun createPrinter(activity: FragmentActivity, onEvent: (MyMsgEvent) -> Unit): PrintLabelListener? {
            return when {
                settingsVm.useNetPrinter -> NetPrinter(onEvent)
                settingsVm.useBtPrinter -> BtPrinter(activity, onEvent)

                else -> {
                    val msg = context.getString(R.string.there_is_no_selected_printer)
                    onEvent(MyMsgEvent(msg, MyEventType.ERROR))
                    null
                }
            }
        }
    }
}