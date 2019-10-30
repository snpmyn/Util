<div align=center><img src="https://github.com/snpmyn/Util/raw/master/image.png"/></div>

[![SNAPSHOT](https://jitpack.io/v/Jaouan/Revealator.svg)](https://jitpack.io/#snpmyn/Util)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a1c9a1b1d1ce4ca7a201ab93492bf6e0)](https://app.codacy.com/project/snpmyn/Util/dashboard)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
[![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19)

### 介绍
工具。

### 说明
* UtilOne基于Java
* UtilTwo基于kotlin

| 模块 | 依赖 |
|:-:|:-:|
| 一方库(UtilOne) | api 'com.github.bumptech.glide:glide:4.10.0'（避重）|
| 一方库(UtilOne) | api 'com.google.android.material:material:1.2.0-alpha01'（避重）|
| 一方库(UtilOne) | api 'io.reactivex:rxandroid:1.2.1'（避重）|
| 一方库(UtilOne) | api 'io.reactivex:rxjava:1.3.8'（避重）|
| 一方库(UtilOne) | api 'com.jakewharton.timber:timber:4.7.1'（避重）|
| 一方库(UtilOne) | api 'com.tencent:mmkv-static:1.0.23'（避重）|
| 一方库(UtilOne) | implementation 'com.getkeepsafe.relinker:relinker:1.3.1' |
| 一方库(UtilOne) | implementation 'com.qw:soulpermission:1.2.2_x' |
| 一方库(UtilOne) | implementation 'org.apache.commons:commons-lang3:3.9' |
| 一方库(UtilTwo) | implementation 'androidx.core:core-ktx:1.2.0-beta01' |
| 一方库(UtilTwo) | implementation "org.jetbrains.kotlin:*kotlin-stdlib-jdk7*:$kotlin_version" |

| 模块 | 权限 |
|:-:|:-:|
| app | android:name="android.permission.WRITE_EXTERNAL_STORAGE"（避重）|
| app | android:name="android.permission.READ_EXTERNAL_STORAGE"（避重）|

### 使用
build.gradle(module)
```
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {   
    repositories {
        google()
        jcenter()       
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.5.1'
        
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url "https://jitpack.io" }             
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
```
build.gradle(app)
```
apply plugin: 'com.android.application'

android {
    ...
    defaultConfig {
        ...      
    }       
    compileOptions {
        sourceCompatibility 1.8
        targetCompatibility 1.8
    }
    configurations.all {
        resolutionStrategy.cacheChangingModulesFor 0, 'seconds'
    }
}

dependencies {
    implementation 'com.github.snpmyn:Util:master-SNAPSHOT'
}
```

### License
```
Copyright 2019 snpmyn

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
