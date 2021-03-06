<div align=center><img src="https://github.com/snpmyn/Util/raw/master/image.png"/></div>

[![SNAPSHOT](https://jitpack.io/v/Jaouan/Revealator.svg)](https://jitpack.io/#snpmyn/Util)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/9a0b01a4875242408cd21a8d20be2604)](https://www.codacy.com/manual/snpmyn/Util?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=snpmyn/Util&amp;utm_campaign=Badge_Grade)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
[![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19)

[![GitHub stars](https://img.shields.io/github/stars/Bigkoo/Util.svg?style=social)](https://github.com/Bigkoo/Util/stargazers) 
[![GitHub forks](https://img.shields.io/github/forks/Bigkoo/Util.svg?style=social)](https://github.com/Bigkoo/Util/network) 
[![GitHub watchers](https://img.shields.io/github/watchers/Bigkoo/Util.svg?style=social)](https://github.com/Bigkoo/Util/watchers)

### 介绍
工具。

### 架构
| 模块 | 说明 | 补充 |
|:-:|:-:|:-:|
| 示例app | 用法举例 | 无 |
| 一方库UtilOne | 基于Java | 无 |
| 一方库UtilTwo | 基于kotlin | 无 |

### 依赖、权限
| 模块 | 依赖 |
|:-:|:-:|
| 示例app | implementation project(path: ':utilone') |
| 示例app | implementation project(path: ':utiltwo') |
| 一方库UtilOne | api 'com.google.android.material:material:1.3.0-beta01'（避重）|
| 一方库UtilOne | api 'com.github.bumptech.glide:glide:4.11.0'（避重）|
| 一方库UtilOne | api 'io.reactivex:rxandroid:1.2.1'（避重）|
| 一方库UtilOne | api 'io.reactivex:rxjava:1.3.8'（避重）|
| 一方库UtilOne | api 'com.jakewharton.timber:timber:4.7.1'（避重）|
| 一方库UtilOne | api 'com.tencent:mmkv-static:1.0.23'（避重）|
| 一方库UtilOne | implementation 'com.getkeepsafe.relinker:relinker:1.3.1' |
| 一方库UtilOne | implementation 'com.qw:soulpermission:1.2.2_x' |
| 一方库UtilOne | implementation 'org.apache.commons:commons-lang3:3.11' |
| 一方库UtilTwo | implementation 'androidx.core:core-ktx:1.5.0-alpha05' |
| 一方库UtilTwo | implementation "org.jetbrains.kotlin:*kotlin-stdlib-jdk7*:$kotlin_version" |

| 模块 | 权限 |
|:-:|:-:|
| 示例app | android:name="android.permission.READ_EXTERNAL_STORAGE"（避重）|
| 示例app | android:name="android.permission.WRITE_EXTERNAL_STORAGE"（避重）|
| 一方库UtilOne | 无 |
| 一方库UtilTwo | 无 |

### 使用
> [SECURITY](https://github.com/snpmyn/Util/blob/master/SECURITY.md)<br>
> 版本快速迭代中，拉取失败暂时查看源码。

build.gradle(module)
```
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {  
    repositories {
        google()
        jcenter()
                
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:4.1.2'     

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
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
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    configurations.all {
        resolutionStrategy.cacheChangingModulesFor 0, 'seconds'
    }
}

dependencies {
    implementation 'com.github.snpmyn:Util:v0.0.1.3X'
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
