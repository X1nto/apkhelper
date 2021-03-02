package com.xinto.apkhelper.callback

import android.content.Context

/**
 * StatusCallback implementation with all methods overridden.
 * Use this if you want to override certain methods only
 * @see StatusCallback
 */
class SimpleStatusCallback : StatusCallback {

    override fun onApkInstall(
        id: Int,
        context: Context
    ) {}

    override fun onApkInstallFailed(
        error: String,
        id: Int,
        context: Context
    ) {}

    override fun onAppUninstall(
        id: Int,
        context: Context
    ) {}

    override fun onAppUninstallFailed(
        error: String,
        id: Int,
        context: Context
    ) {}

}
