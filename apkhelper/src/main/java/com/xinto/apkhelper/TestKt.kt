package com.xinto.apkhelper

import android.content.Context
import android.util.Log
import com.xinto.apkhelper.callback.SimpleStatusCallback

class TestKt {
    fun test() {
        statusCallback = object : SimpleStatusCallback() {
            override fun onApkInstall(id: Int, context: Context) {
                Log.i(logTag, "Successfully installed an APK")
            }
        }
        statusCallback = object : SimpleStatusCallback() {
            override fun onApkInstall(id: Int, context: Context) {
                when (id) {
                    1 -> Log.i(logTag, "Installed the first APK")
                    2 -> Log.i(logTag, "Installed the second APK")
                }
            }
        }
    }
}