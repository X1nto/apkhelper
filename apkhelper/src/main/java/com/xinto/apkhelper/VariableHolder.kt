package com.xinto.apkhelper

import android.content.Context
import com.xinto.apkhelper.callback.StatusCallback

private var statusCallbackClass: StatusCallback? = null
private var logTagString = "APKHelper"

var statusCallback
    get() = statusCallbackClass
    set(value) {
        statusCallbackClass = value
    }

var logTag
    get() = logTagString
    set(value) {
        logTagString = value
    }

inline fun setStatusCallback(
    crossinline onInstall: (id: Int, context: Context) -> Unit = {_, _ ->},
    crossinline onInstallFailed: (error: String, id: Int, context: Context) -> Unit = {_, _, _ ->},
    crossinline onUninstall: (id: Int, context: Context) -> Unit = {_, _ ->},
    crossinline onUninstallFailed: (error: String, id: Int, context: Context) -> Unit = {_, _, _ ->}
) {
    statusCallback = object : StatusCallback {
        override fun onApkInstall(id: Int, context: Context) = onInstall(id, context)
        override fun onApkInstallFailed(error: String, id: Int, context: Context) = onInstallFailed(error, id, context)
        override fun onAppUninstall(id: Int, context: Context) = onUninstall(id, context)
        override fun onAppUninstallFailed(error: String, id: Int, context: Context) = onUninstallFailed(error, id, context)
    }
}