/*
 * Copyright 2021 Xinto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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