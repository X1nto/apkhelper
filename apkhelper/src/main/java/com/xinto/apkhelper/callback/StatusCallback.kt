package com.xinto.apkhelper.callback

import android.content.Context

/**
 * Callback interface used to invoke actions
 */
interface StatusCallback {

    /**
     * Called on successful installation
     * @param id ID of installation, default is 0
     * @param context Service context
     */
    fun onApkInstall(id: Int, context: Context)

    /**
     * Called on installation failure
     * @param error error output
     * @param id ID of installation, default is 0
     * @param context Service context
     */
    fun onApkInstallFailed(error: String, id: Int, context: Context)

    /**
     * Called on successful uninstallation
     * @param id ID of uninstallation, default is 0
     * @param context Service context
     */
    fun onAppUninstall(id: Int, context: Context)

    /**
     * Called on uninstallation failure
     * @param error error output
     * @param id ID of uninstallation, default is 0
     * @param context Service context
     */
    fun onAppUninstallFailed(error: String, id: Int, context: Context)

}