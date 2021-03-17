# APKHelper
A simple library which allows you to install and uninstall apps on the device

## Installation

### Groovy DSL

```groovy
//In top level build.gradle
allProjects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}

//In app's build.gradle
dependencies {
    implementation 'com.github.xinto:apkhelper:1.0.0'
}
```

### Kotlin DSL

```kotlin
//In top level build.gradle.kts
allProjects {
    repositories {
        maven(url = "https://jitpack.io")
    }
}

//In app's build.gradle.kts
dependencies {
    implementation("com.github.xinto:apkhelper:1.0.0")
}
```

## Usage

### AndroidManifest

```xml
<!-- Required for apk installation -->
<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />

<!-- Required for apk uninstallation -->
<uses-permission android:name="android.permission.REQUEST_DELETE_PACKAGES" />

<application
    android:icon="@mipmap/ic_launcher"
    android:label="@string/app_name">

    <service android:name="com.xinto.apkhelper.services.PackageManagerService" />

</application/>
```

### Register a listener

<details>
<summary>Java</summary>
Listener with all methods required to be overridden
    
```java
VariableHolderKt.setStatusCallback(new StatusCallback() {
    @Override
    public void onApkInstall(int id, @NotNull Context context) {
        Log.i(VariableHolderKt.getLogTag(), "Successfully installed an APK");
    }

    @Override
    public void onApkInstallFailed(@NotNull String error, int id, @NotNull Context context) {
        Log.i(VariableHolderKt.getLogTag(), "Failed to install an APK: " + error);
    }

    @Override
    public void onAppUninstall(int id, @NotNull Context context) {
        Log.i(VariableHolderKt.getLogTag(), "Successfully uninstalled an app");
    }

    @Override
    public void onAppUninstallFailed(@NotNull String error, int id, @NotNull Context context) {
        Log.i(VariableHolderKt.getLogTag(), "Failed to uninstall app: " + error);
    }
});
```
A simpler listener allowing you to override only the methods you need
    
```java
VariableHolderKt.setStatusCallback(new SimpleStatusCallback() {
    @Override
    public void onApkInstall(int id, @NotNull Context context) {
        Log.i(VariableHolderKt.getLogTag(), "Successfully installed an APK"); 
    }
});
```


</details>

<details>
<summary>Kotlin</summary>

This method allows you to only provide callback for methods you actually need

```kotlin
statusCallback = statusCallbackBuilder(
    onInstall = { id, context ->
        Log.i(logTag, "Successfully installed an APK")
    },
    onInstallFailed = { error, id, context ->
        Log.i(logTag, "Failed to install an APK: $error")
    },
    onUninstall = { id, context ->
        Log.i(logTag, "Successfully uninstalled an app")
    },
    onUninstallFailed = { error, id, context ->
        Log.i(logTag, "Failed to uninstall app: $error")
    }
)
```

</details>

### Register a listener for different callbacks

<details>
<summary>Java</summary>

Listener with all methods required to be overridden

```java
VariableHolderKt.setStatusCallback(new StatusCallback() {
    @Override
    public void onApkInstall(int id, @NotNull Context context) {
        switch (id) {
            case 1:
                Log.i(VariableHolderKt.getLogTag(), "Installed the first APK");
                break;
            case 2:
                Log.i(VariableHolderKt.getLogTag(), "Installed the second APK");
                break;
        }
    }

    @Override
    public void onApkInstallFailed(@NotNull String error, int id, @NotNull Context context) {
        switch (id) {
            case 1:
                Log.i(VariableHolderKt.getLogTag(), "Failed to install the first APK");
                break;
            case 2:
                Log.i(VariableHolderKt.getLogTag(), "Failed to install the second APK");
                break;
        }
    }

    @Override
    public void onAppUninstall(int id, @NotNull Context context) {
        switch (id) {
            case 1:
                Log.i(VariableHolderKt.getLogTag(), "Uninstalled the first app");
                break;
            case 2:
                Log.i(VariableHolderKt.getLogTag(), "Uninstalled the second app");
                break;
        }
    }

    @Override
    public void onAppUninstallFailed(@NotNull String error, int id, @NotNull Context context) {
        switch (id) {
            case 1:
                Log.i(VariableHolderKt.getLogTag(), "Failed to uninstall first app");
                break;
            case 2:
                Log.i(VariableHolderKt.getLogTag(), "Failed to uninstall second app");
                break;
        }
    }
});
```
A simpler listener allowing you to override only the methods you need

```java
VariableHolderKt.setStatusCallback(new SimpleStatusCallback() {
    @Override
    public void onApkInstall(int id, @NotNull Context context) {
        switch (id) {
            case 1:
                Log.i(VariableHolderKt.getLogTag(), "Installed the first APK");
                break;
            case 2:
                Log.i(VariableHolderKt.getLogTag(), "Installed the second APK");
                break;
        }
    }
});

```
</details>

<details>
<summary>Kotlin</summary>

This method allows you to only provide callback for methods you actually need

```kotlin
statusCallback = statusCallbackBuilder(
    onInstall = { id, context ->
        when (id) {
            1 -> Log.i(logTag, "Installed the first APK")
            2 -> Log.i(logTag, "Installed the second APK")
        }
    },
    onInstallFailed = { error, id, context ->
        when (id) {
            1 -> Log.i(logTag, "Failed to install the first APK")
            2 -> Log.i(logTag, "Failed to install the second APK")
        }
    },
    onUninstall = { id, context ->
        when (id) {
            1 -> Log.i(logTag, "Uninstalled the first app")
            2 -> Log.i(logTag, "Uninstalled the second app")
        }
    },
    onUninstallFailed = { error, id, context ->
        when (id) {
            1 -> Log.i(logTag, "Failed to uninstall the first app")
            2 -> Log.i(logTag, "Failed to uninstall the second app")
        }
    }
)
```

</details>

### Install an APK

<details>
<summary>Java</summary>

```java
//Inside AppCompatActivity, can be done from anywhere where context is accessible
//ID is not optional because java doesn't support optional parameters
PMKt.installApk(getExternalFilesDir("apks").getPath() + "/someapk.apk", this, 0);
```
</details>

<details>
<summary>Kotlin</summary>

```java
//Inside AppCompatActivity, can be done from anywhere where context is accessible
//ID is optional, default is 0
installApk(getExternalFilesDir("apks")?.path + "/someapk.apk", this)
```
</details>

### Install split APKs

<details>
<summary>Java</summary>

```java
//Inside AppCompatActivity, can be done from anywhere where context is accessible
//ID is not optional because java doesn't support optional parameters
PMKt.installSplitApks(getExternalFilesDir("splitApks").getPath(), this, 0);
```
</details>

<details>
<summary>Kotlin</summary>

```java
//Inside AppCompatActivity, can be done from anywhere where context is accessible
//ID is optional, default is 0
installSplitApks(getExternalFilesDir("splitapks")?.path.toString(), this)
```
</details>

### Uninstall an app from the device

<details>
<summary>Java</summary>

```java
//Inside AppCompatActivity, can be done from anywhere where context is accessible
//ID is not optional because java doesn't support optional parameters
PMKt.uninstallApk("com.some.application", this, 0);
```
</details>

<details>
<summary>Kotlin</summary>

```kotlin
//Inside AppCompatActivity, can be done from anywhere where context is accessible
//ID is optional, default is 0
uninstallApk("com.some.application", this)
```
</details>

### Customization

By default, whenever library logs something, it uses the "APKHelper" tag, you can customize that using the following code:

<details>
<summary>Java</summary>

```java
VariableHolderKt.setLogTag("Your custom log tag");
```
</details>

<details>
<summary>Kotlin</summary>

```kotlin
logTag = "Your custom log tag"
```
</details>

## TODO
- [ ] Add a sample app

## LICENSE
```
Copyright 2021 Xinto
  
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```