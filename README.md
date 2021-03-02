# APKHelper
A simple library which allows you to install and uninstall apps on the device

## Installation

### Groovy DSL

```groovy

dependencies {
    
    implementation 'com.xinto.apkhelper:apkhelper:1.0.0'
    
}

```

### Kotlin DSL

```groovy

dependencies {
    
    implementation("com.xinto.apkhelper:apkhelper:1.0.0")
    
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

    <!-- Required for apk installation -->
    <service android:name="com.xinto.apkhelper.services.AppInstallService" />
    
    <!-- Required for apk uninstallation -->
    <service android:name="com.xinto.apkhelper.services.AppUninstallService" />

</application/>

```

### Register a listener

<details>
<summary>Java</summary>
Listener with all methods required to be overriden
    
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

Listener with all methods required to be overriden

```kotlin
statusCallback = object : StatusCallback {
    override fun onApkInstall(id: Int, context: Context) {
        Log.i(logTag, "Successfully installed an APK")
    }

    override fun onApkInstallFailed(error: String, id: Int, context: Context) {
        Log.i(logTag, "Failed to install an APK: $error")
    }

    override fun onAppUninstall(id: Int, context: Context) {
        Log.i(logTag, "Successfully uninstalled an app")
    }

    override fun onAppUninstallFailed(error: String, id: Int, context: Context) {
        Log.i(logTag, "Failed to uninstall app: $error")
    }
}
```
A simpler listener allowing you to override only the methods you need
```kotlin
statusCallback = object : SimpleStatusCallback() {
    override fun onApkInstall(id: Int, context: Context) {
        Log.i(logTag, "Successfully installed an APK")
    }
}
```


</details>

### Register a listener for different callbacks

<details>
<summary>Java</summary>

Listener with all methods required to be overriden

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
                Log.i(VariableHolderKt.getLogTag(), "Failed to install the first APK");
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

Listener with all methods required to be overriden

```kotlin
statusCallback = object : StatusCallback {
    override fun onApkInstall(id: Int, context: Context) {
        when (id) {
            1 -> Log.i(logTag, "Installed the first APK")
            2 -> Log.i(logTag, "Installed the second APK")
        }
    }

    override fun onApkInstallFailed(error: String, id: Int, context: Context) {
        when (id) {
            1 -> Log.i(logTag, "Failed to install the first APK")
            2 -> Log.i(logTag, "Failed to install the second APK")
        }
    }

    override fun onAppUninstall(id: Int, context: Context) {
        when (id) {
            1 -> Log.i(logTag, "Uninstalled the first app")
            2 -> Log.i(logTag, "Uninstalled the second app")
        }
    }

    override fun onAppUninstallFailed(error: String, id: Int, context: Context) {
        when (id) {
            1 -> Log.i(logTag, "Failed to uninstall first app")
            2 -> Log.i(logTag, "Failed to uninstall second app")
        }
    }
}
```

A simpler listener allowing you to override only the methods you need

```kotlin
statusCallback = object : SimpleStatusCallback() {
    override fun onApkInstall(id: Int, context: Context) {
        when (id) {
            1 -> Log.i(logTag, "Failed to install the first APK")
            2 -> Log.i(logTag, "Installed the second APK")
        }
    }
}
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

```java
//Inside AppCompatActivity, can be done from anywhere where context is accessible
//ID is optional, default is 0
uninstallApk("com.some.application", this)
```
</details>

## TODO
- [ ] Add a sample app
