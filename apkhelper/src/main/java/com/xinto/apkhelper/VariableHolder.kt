package com.xinto.apkhelper

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