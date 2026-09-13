# GitHub Actions 自动编译指南

## 步骤

### 1. 创建GitHub仓库
1. 去 https://github.com/new
2. 创建新仓库（公开或私有都行）
3. 记住仓库地址

### 2. 上传代码
在 `remote-touchpad` 目录下执行：

```bash
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin https://github.com/你的用户名/你的仓库名.git
git push -u origin main
```

### 3. 触发编译
- **自动触发**：推送代码后自动开始编译
- **手动触发**：GitHub仓库页面 → Actions → Build Android APK → Run workflow

### 4. 下载APK
1. 编译完成后（约5-8分钟）
2. 进入 Actions 页面
3. 点击最新的工作流运行记录
4. 下滚到底部的 **Artifacts** 区域
5. 下载 `RemoteTouchpad-APK.zip`
6. 解压得到 APK 文件

---

## 快速命令（如果已有Git）

```bash
cd remote-touchpad
git init
git add .
git commit -m "Remote Touchpad v1.0"
git branch -M main
git remote add origin https://github.com/你的用户名/仓库名.git
git push -u origin main
```

然后去 GitHub 网页看 Actions 进度。

---

## 本地测试（可选）

如果想本地验证配置：
```bash
# 需要Docker
act -j build
```

---

## 编译完成后

- Windows exe：`windows-server/dist/RemoteTouchpad.exe` (已有)
- Android APK：从 GitHub Actions Artifacts 下载

两个文件都准备好就能用了。
