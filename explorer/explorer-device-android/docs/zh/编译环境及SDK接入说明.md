 * [准备开发环境](#准备开发环境)
 * [创建一个新的Android工程](#创建一个新的Android工程)
 * [引用方式](#引用方式)

## 准备开发环境
SDK Demo示例中使用的是Android Studio版本为4.0.1，gradle版本为6.3。
Android Studio 可在 [Android developers官网](https://developer.android.com/studio) 下载，Android 开发教程，请参考 Android developers 官网文档。

## 创建一个新的Android工程

## 引用方式
- 集成 SDK 方式
 -  gradle 工程 正式版SDK 远程构建

    在应用模块的build.gradle中配置，具体版本号可参考 [Latest release](https://github.com/tencentyun/iot-device-java/releases) 版本 
    ``` gr
    dependencies {
        implementation 'com.tencent.iot.explorer:explorer-device-android:x.x.x'
    }
    ```
 -  gradle 工程 snapshot版SDK 远程构建

    > 建议使用正式版SDK，SNAPSHOT版本会静默更新，使用存在风险

    在工程的build.gradle中配置仓库url
    ``` gr
    allprojects {
        repositories {
            google()
            jcenter()
            maven {
                url "https://oss.sonatype.org/content/repositories/snapshots"
            }
        }
    }
    ```
    在应用模块的build.gradle中配置，具体版本号可参考 [Latest release](https://github.com/tencentyun/iot-device-java/releases) 版本，末位+1
    ``` gr
    dependencies {
        implementation 'com.tencent.iot.explorer:explorer-device-android:x.x.x-SNAPSHOT'
    }
    ```
 -  依赖本地sdk源码 构建
    修改应用模块的 **[build.gradle](../../../device-android-demo/build.gradle)**，使应用模块依赖 [explorer-device-android](../..)源码，示例如下：
    
     ```gr
    dependencies {
        implementation project(':explorer:explorer-device-android')
    }
     ```

Demo示例工程使用的是 依赖本地sdk源码 构建方式。
