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

package com.xinto.apkhelper.services

import android.app.Service
import android.content.Intent
import android.content.pm.PackageInstaller
import android.os.IBinder
import android.util.Log
import com.xinto.apkhelper.logTag
import com.xinto.apkhelper.statusCallback

class AppUninstallService : Service() {

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val id = intent.getIntExtra("id", 0)
        when (intent.getIntExtra(PackageInstaller.EXTRA_STATUS, -999)) {
            PackageInstaller.STATUS_PENDING_USER_ACTION -> {
                Log.i(logTag, "Requesting user confirmation for uninstallation")
                val confirmationIntent = intent.getParcelableExtra<Intent>(Intent.EXTRA_INTENT)
                confirmationIntent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                try {
                    startActivity(confirmationIntent)
                } catch (e: Exception) {
                    Log.w(logTag, "Unable to start confirmation activity: ${e.stackTraceToString()}")
                }
            }
            PackageInstaller.STATUS_SUCCESS -> statusCallback?.onAppUninstall(id, this)
            else -> {
                intent.getStringExtra(PackageInstaller.EXTRA_STATUS_MESSAGE)?.let {
                    statusCallback?.onAppUninstallFailed(it, id, this)
                }
            }
        }
        stopSelf()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


}