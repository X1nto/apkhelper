package com.xinto.apkhelper.services

import android.app.Service
import android.content.Intent
import android.content.pm.PackageInstaller
import android.os.IBinder
import android.util.Log
import com.xinto.apkhelper.logTag
import com.xinto.apkhelper.statusCallback

class AppInstallService : Service() {

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val id = intent.getIntExtra("id", 0)
        when (intent.getIntExtra(PackageInstaller.EXTRA_STATUS, -999)) {
            PackageInstaller.STATUS_PENDING_USER_ACTION -> {
                Log.i(logTag, "Requesting user confirmation for installation")
                val confirmationIntent = intent.getParcelableExtra<Intent>(Intent.EXTRA_INTENT)
                confirmationIntent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                try {
                    startActivity(confirmationIntent)
                } catch (e: Exception) {
                    Log.w(logTag, "Unable to start confirmation activity: ${e.stackTraceToString()}")
                }
            }
            PackageInstaller.STATUS_SUCCESS -> {
                statusCallback?.onApkInstall(id, this)
            }
            else -> {
                intent.getStringExtra(PackageInstaller.EXTRA_STATUS_MESSAGE)?.let {
                    statusCallback?.onApkInstallFailed(it, id, this)
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