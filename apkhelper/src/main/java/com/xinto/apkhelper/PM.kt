package com.xinto.apkhelper

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import com.xinto.apkhelper.services.AppInstallService
import com.xinto.apkhelper.services.AppUninstallService
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

/**
 * Install a regular APK
 * @param apkPath The path to APK
 * @param context Application/Activity context
 * @param id ID of the installation, can be useful if you want to trigger different triggers after apk installation
 */
fun installApk(apkPath: String, context: Context, id: Int = 0) {
    installApk(File(apkPath), context, id)
}

/**
 * Install a regular APK
 * @param apk The APK to install
 * @param context Application/Activity context
 * @param id ID of the installation, can be useful if you want to trigger different triggers after apk installation
 */
fun installApk(apk: File, context: Context, id: Int = 0) {
    val callbackIntent = Intent(context, AppInstallService::class.java).apply {
        putExtra("id", id)
    }
    val pendingIntent = PendingIntent.getService(context, 0, callbackIntent, 0)
    val packageInstaller = context.packageManager.packageInstaller
    val params = PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL)
    val sessionId: Int
    var session: PackageInstaller.Session? = null
    try {
        sessionId = packageInstaller.createSession(params)
        session = packageInstaller.openSession(sessionId)
        val inputStream: InputStream = FileInputStream(apk)
        val outputStream = session.openWrite("install", 0, -1)
        val buffer = ByteArray(65536)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) {
            outputStream.write(buffer, 0, length)
        }
        session.fsync(outputStream)
        inputStream.close()
        outputStream.close()
        session.commit(pendingIntent.intentSender)
    } catch (e: Exception) {
        statusCallback?.onApkInstallFailed(e.stackTraceToString(), id, context)
    } finally {
        session?.close()
    }
}

/**
 *
 * Install provided split APK files
 * @param apksPath path to split APKs, library automatically filters out other file types but .apk
 * @param context Application/Activity context
 * @param id ID of installation, can be useful if you want to trigger different triggers after apk installation
 * @throws IllegalArgumentException if apksPath is not a directory
 */
@Throws(IllegalArgumentException::class)
fun installSplitApks(apksPath: String, context: Context, id: Int = 0) {
    val files = File(apksPath).listFiles()
    if (files != null) {
        installSplitApks(files.toList(), context, id)
    } else {
        throw IllegalArgumentException("Path does not denote a directory")
    }
}

/**
 * Install provided split APK files
 * @param apks list of split APKs to install, library automatically filters out other file types but .apk
 * @param context Application/Activity context
 * @param id ID of installation, can be useful if you want to trigger different triggers after apk installation
 */
fun installSplitApks(apks: List<File>, context: Context, id: Int = 0) {
    val callbackIntent = Intent(context, AppInstallService::class.java).apply {
        putExtra("id", id)
    }
    val pendingIntent = PendingIntent.getService(context, 0, callbackIntent, 0)
    val packageInstaller = context.packageManager.packageInstaller
    var session: PackageInstaller.Session? = null
    val sessionId: Int
    val sessionParams = PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL)
    try {
        sessionId = packageInstaller.createSession(sessionParams)
        session = packageInstaller.openSession(sessionId)
        apks.filter { it.extension == "apk" }.forEach { apk ->
            val inputStream = FileInputStream(apk)
            val outputStream = session.openWrite(apk.name, 0, apk.length())
            val buffer = ByteArray(65536)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }
            session.fsync(outputStream)
            inputStream.close()
            outputStream.close()
        }
        session.commit(pendingIntent.intentSender)
    } catch (e: Exception) {
        statusCallback?.onApkInstallFailed(e.stackTraceToString(), id, context)
    } finally {
        session?.close()
    }
}

/**
 * @param pkg Package to uninstall
 * @param context Application/Activity context
 * @param id ID of uninstallation, can be if you want to trigger different triggers after apk uninstallation
 */
@SuppressLint("MissingPermission")
fun uninstallApk(pkg: String, context: Context, id: Int = 0) {
    val callbackIntent = Intent(context, AppUninstallService::class.java)
    val pendingIntent = PendingIntent.getService(context, 0, callbackIntent, 0)
    try {
        context.packageManager.packageInstaller.uninstall(pkg, pendingIntent.intentSender)
    } catch (e: Exception) {
        statusCallback?.onAppUninstallFailed(e.stackTraceToString(), id, context)
    }
}