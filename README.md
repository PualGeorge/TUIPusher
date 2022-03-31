本文档主要介绍如何运行TUIPusher组件的，以及如何在别的工程中集成TUIPusher组件

## 目录结构

```
TUIPusher
├─ app          // app模块，主要提供TUIPusher的推流测试页面
└─ tuipusher    // TUIPusher组件的推流逻辑封装
```

## 环境准备
- 最低兼容 Android 4.1（SDK API Level 16），建议使用 Android 5.0 （SDK API Level 21）及以上版本
- Android Studio 3.5及以上版本
- App 要求 Android 4.1及以上设备

## 运行示例

### 前提条件
您已 [注册腾讯云](https://cloud.tencent.com/document/product/378/17985) 账号，并完成 [实名认证](https://cloud.tencent.com/document/product/378/3629)。

### 第一步：申请 SDKAPPID 和 SECRETKEY
1. 登录实时音视频控制台，选择【开发辅助】>【[快速跑通Demo](https://console.cloud.tencent.com/trtc/quickstart)】。
2. 单击【立即开始】，输入您的应用名称，例如`TestTRTC`，单击【创建应用】。
<img src="https://main.qcloudimg.com/raw/169391f6711857dca6ed8cfce7b391bd.png" width="650" height="295"/>
3. 创建应用完成后，单击【我已下载，下一步】，可以查看 SDKAppID 和密钥信息。

### 第二步：开通移动直播服务
1. [开通直播服务并绑定域名](https://console.cloud.tencent.com/live/livestat) 如果还没开通，点击申请开通，之后在域名管理中配置推流域名和拉流域名
2. [获取SDK的测试License](https://console.cloud.tencent.com/live/license) 
3. [配置推拉流域名](https://console.cloud.tencent.com/live/domainmanage)

### 第三步：下载源码，配置工程
1. 克隆或者直接下载此仓库源码，使用 Android Studio（3.5及以上的版本）打开源码工程`TUIPusher`。
2. 找到并打开`TUIPusher/app/src/main/java/com/tencent/qcloud/tuikit/tuipusher/demo/debug/GenerateTestUserSig.java`文件。
3. 设置`GenerateTestUserSig.java`文件中的相关参数：
  - `SDKAPPID`：默认为 PLACEHOLDER ，请设置为实际的 SDKAppID；
  - `SECRETKEY`：默认为空字符串，请设置为实际的密钥信息；
  - `LICENSEURL`：默认为 PLACEHOLDER ，请设置为实际的License Url信息；
  - `LICENSEURLKEY`：默认为 PLACEHOLDER ，请设置为实际的License Key信息；
  - `PUSH_DOMAIN`：RTMP协议推流域名，有使用RTMP协议推流时配置即可
  - `PLAY_DOMAIN`：可选：配置的拉流域名
  - `LIVE_URL_KEY`：可选：如果开通鉴权配置的鉴权Key
4. 修改 app模块下的 `build.gradle` 文件中 `applicationId` 字段 为License 信息所对应的包名


### 第四步：编译运行
用 Android Studio 打开该项目，连上Android设备，编译并运行。

### 第五步：示例体验
Tips：TUIPusher 组件仅可体验推流端效果，如需观看拉流效果请使用 [TUIPlayer](https://github.com/LiteAV-TUIKit/TUIPlayer)组件：

设备 A

步骤 1：在欢迎页，输入用户名(请确保用户名唯一性，不能与其他用户重复)，比如111；
步骤 2：点击开始直播，即可体验；

设备 B

步骤 1：在欢迎页，输入用户名(请确保用户名唯一性，不能与其他用户重复)，比如222；
步骤 2：点击开始直播，即可体验；
步骤 3：输入要PK的房间号（用户名）如111，点击 `PK` ，等待用户 111 同意后，即可与用户 111 进行 `PK`；

我们在自己的[小直播](https://github.com/tencentyun/XiaoZhiBo)工程中也使用了该TUIPlayer组件，可以参考。

## 问题答疑
1、我们官网文档[常见问题](https://cloud.tencent.com/document/product/454/7998)中整理了一些常见的问题，如果遇到相同的问题，可以参考上面的解决方案
2、可以加入我们的 TUIKIT 答疑群，在群里我们有专人进行答疑
