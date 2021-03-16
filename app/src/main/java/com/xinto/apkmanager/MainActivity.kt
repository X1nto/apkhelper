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

package com.xinto.apkmanager

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.xinto.apkhelper.installApk
import com.xinto.apkhelper.logTag
import com.xinto.apkhelper.statusCallback
import com.xinto.apkhelper.statusCallbackBuilder
import com.xinto.apkmanager.databinding.ActivityMainBinding
import java.io.File
import java.net.URI

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val regularapk = "android.resource://$packageName/raw/regular.apk"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        statusCallback = statusCallbackBuilder(
            onInstall = { id, _ ->
                when (id) {
                    69 -> Toast.makeText(this, "Successfully installed the regular apk", Toast.LENGTH_SHORT).show()
                    420 -> Toast.makeText(this, "Successfully installed split apks", Toast.LENGTH_SHORT).show()
                }
            },
            onInstallFailed = { error, id, _ ->
                when (id) {
                    69 ->  {
                        Toast.makeText(this, "Failed to install the regular apk", Toast.LENGTH_SHORT).show()
                        Log.e(logTag, error)
                    }
                    420 -> {
                        Toast.makeText(this, "Failed to install split apks", Toast.LENGTH_SHORT).show()
                        Log.e(logTag, error)
                    }
                }
            }
        )

        binding.installRegular.setOnClickListener {
            installApk(File(URI.create(regularapk)), this)
        }

    }

}