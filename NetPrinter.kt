package com.example.app

import android.util.Log
import com.example.app.R
import com.example.app.MyApp.Companion.context
import com.example.app.MyApp.Companion.settingsVm
import com.example.app.ui.snackBar.MyEventType
import java.io.IOException
import java.net.ConnectException
import java.net.Socket
import java.net.UnknownHostException

open class NetPrinter(private val onEvent: (MyMsgEvent) -> Unit) :
    Printer.PrintLabelListener {

    private val tag = this::class.java.enclosingClass?.simpleName ?: this::class.java.simpleName

    override fun printLabel(printThis: String, qty: Int, onFinish: (Boolean) -> Unit) {
        val ipPrinter = settingsVm.ipNetPrinter
        val portPrinter = settingsVm.portNetPrinter

        Log.v(tag, "Printer IP: $ipPrinter ($portPrinter)")
        Log.v(tag, printThis)

        val t = object : Thread() {
            override fun run() {
                try {
                    val socket = Socket(ipPrinter, portPrinter)
                    val os = socket.outputStream
                    for (i in 0 until qty) {
                        os.write(printThis.toByteArray())
                    }
                    os.flush()
                    socket.close()
                    onFinish(true)
                } catch (e: UnknownHostException) {
                    e.printStackTrace()
                    sendEvent(
                        "${context.getString(R.string.unknown_host)}: $ipPrinter ($portPrinter)",
                        MyEventType.ERROR
                    )
                    onFinish(false)
                } catch (e: ConnectException) {
                    e.printStackTrace()
                    sendEvent(
                        "${context.getString(R.string.error_connecting_to)}: $ipPrinter ($portPrinter)",
                        MyEventType.ERROR
                    )
                    onFinish(false)
                } catch (e: IOException) {
                    e.printStackTrace()
                    sendEvent(
                        "${context.getString(R.string.error_printing_to)} $ipPrinter ($portPrinter)",
                        MyEventType.ERROR
                    )
                    onFinish(false)
                }
            }
        }
        t.start()
    }

    private fun sendEvent(message: String, type: MyEventType) {
        onEvent(MyMsgEvent(message, type))
    }
}