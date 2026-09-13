# 打包成exe
@echo off
echo 安装打包工具...
pip install pyinstaller

echo.
echo 打包中...
pyinstaller --onefile --name RemoteTouchpad server.py

echo.
echo 完成！exe位置: dist\RemoteTouchpad.exe
pause
