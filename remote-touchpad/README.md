# Remote Touchpad

极简远程触控板，Windows服务端 + Android客户端

## Windows 服务端

### 安装运行
```bash
cd windows-server
pip install -r requirements.txt
python server.py
```

服务会在8765端口启动，终端会显示你的IP地址。

### 打包成exe（可选）
```bash
pip install pyinstaller
pyinstaller --onefile --noconsole server.py
```
生成的exe在`dist/`目录。

## Android 客户端

### 编译APK
1. 用Android Studio打开`android-client`目录
2. Build > Build Bundle(s) / APK(s) > Build APK(s)
3. 生成的APK在`app/build/outputs/apk/release/`

### 使用
1. 确保手机和电脑在同一局域网
2. 打开APP，输入Windows端显示的IP地址
3. 点击"连接"
4. 触摸屏幕移动鼠标，点击左右键按钮

## 体积
- Windows端：~10MB (打包后)
- Android端：~2MB (APK)

## 协议
WebSocket JSON:
- `{"action": "move", "dx": 10, "dy": 5}`
- `{"action": "click", "button": "left"}`
- `{"action": "scroll", "delta": 120}`
