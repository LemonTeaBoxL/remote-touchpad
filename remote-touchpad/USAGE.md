# 使用说明

## Windows端 - 快速启动

### 方法1：直接运行（需要Python）
1. 双击 `start.bat`
2. 等待安装依赖并启动服务
3. 记住终端显示的IP地址（例如：`192.168.1.100`）

### 方法2：打包成exe（无需Python环境）
1. 双击 `build.bat` 打包
2. 运行 `dist\RemoteTouchpad.exe`
3. 记住终端显示的IP地址

---

## Android端 - 编译安装

### 用Android Studio编译
1. 打开Android Studio
2. File > Open，选择 `android-client` 文件夹
3. 等待Gradle同步完成
4. Build > Build Bundle(s) / APK(s) > Build APK(s)
5. 生成的APK在：`app/build/outputs/apk/debug/app-debug.apk`
6. 传到手机安装

### 用命令行编译（需要Android SDK）
```bash
cd android-client
./gradlew assembleRelease
# APK位置: app/build/outputs/apk/release/app-release-unsigned.apk
```

---

## 使用步骤

1. **确保同一局域网**：手机和电脑连同一个WiFi
2. **启动Windows服务端**：运行 `start.bat` 或 `RemoteTouchpad.exe`
3. **打开Android APP**：输入服务端显示的IP地址（如 `192.168.1.100`）
4. **点击"连接"**
5. **开始使用**：
   - 在触控板区域滑动 = 移动鼠标
   - 点击"左键" = 鼠标左键
   - 点击"右键" = 鼠标右键

---

## 体积

- **Windows端**：Python脚本 ~2KB，打包exe约 10MB
- **Android端**：APK约 2-3MB

---

## 常见问题

**连接失败？**
- 检查防火墙是否阻止了8765端口
- 确认手机和电脑在同一WiFi下
- Windows服务端运行中显示的IP地址是否输入正确

**鼠标移动太快/太慢？**
- 修改 `MainActivity.kt` 第75行的灵敏度系数（当前是 `* 2`）

**需要添加键盘功能？**
- 服务端已支持，Android端可以加个输入框调用 `sendCommand("key", mapOf("key" to "A"))`
