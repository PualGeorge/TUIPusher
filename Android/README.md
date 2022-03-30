# TUIPusher Android 示例工程快速跑通

## 目录结构

```
Android
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

### 第一步：创建TRTC的应用
1. 一键进入腾讯云实时音视频控制台的[应用管理](https://console.cloud.tencent.com/trtc/app)界面，选择创建应用，输入应用名称，例如 `TUIKitDemo` ，单击 **创建**；
2. 点击对应应用条目后**应用信息**，具体位置如下下图所示：
    <img src="https://qcloudimg.tencent-cloud.cn/raw/62f58d310dde3de2d765e9a460b8676a.png" width="900">
3. 进入应用信息后，按下图操作，记录SDKAppID和密钥：
    <img src="https://qcloudimg.tencent-cloud.cn/raw/bea06852e22a33c77cb41d287cac25db.png" width="900">
4. 进入腾讯云直播[LICENSE管理](https://console.cloud.tencent.com/live/license)节面，创建应用并绑定LICENSE
![](https://qcloudimg.tencent-cloud.cn/raw/886dbc5cf9cea301a69a7c06c80390d4.png)
创建成功后请记录 ` License Key `和 `License URL`，便于在运行时使用。
![](https://qcloudimg.tencent-cloud.cn/raw/5bca99c4b00f23eaa763310dc475ec1e.png)
5. [配置推拉流域名](https://console.cloud.tencent.com/live/domainmanage)

### 第三步：下载源码，配置工程
1. 克隆或者直接下载此仓库源码，使用 Android Studio（3.5及以上的版本）打开源码工程`TUIPusher/Android`。
2. 找到并打开`Android/app/src/main/java/com/tencent/qcloud/tuikit/tuipusher/demo/debug/GenerateTestUserSig.java`文件。
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

我们在自己的[小直播](https://github.com/tencentyun/XiaoZhiBo)工程中也使用了该TUIPusher组件，可以参考。

## 常见问题
- [TUI 场景化解决方案常见问题](https://cloud.tencent.com/developer/article/1952880)
- 欢迎加入 QQ 群：592465424，进行技术交流和反馈~
