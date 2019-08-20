### 介绍
工具

### 说明
* UtilOne基于Java
* UtilTwo基于Kotlin

### 依赖
#### AndroidLibrary - UtilOne
* api 'com.github.bumptech.glide:glide:4.9.0'（避重）
* api 'com.google.android.material:material:1.1.0-alpha09'（避重）
* api 'com.jakewharton.timber:timber:4.7.1'（避重）
* implementation 'com.qw:soulpermission:1.2.1_x'
* implementation 'org.apache.commons:commons-lang3:3.9'

#### AndroidLibrary - UtilTwo
* implementation 'androidx.core:core-ktx:1.2.0-alpha03'
* implementation "org.jetbrains.kotlin:*kotlin-stdlib-jdk7*:$kotlin_version"

### 权限
#### app
* android:name="android.permission.WRITE_EXTERNAL_STORAGE"（避重）
* android:name="android.permission.READ_EXTERNAL_STORAGE"（避重）
