# GitHub Actions Workflow 更新指南

## 概述

此文档说明如何将 Release-Core APK 构建从未签名状态更新为调试签名状态，解决 Issue #5 中提出的签名问题。

## 主要变更

### 🔧 核心修改

将 Release APK 构建步骤从：
```yaml
- name: Build APK (Release - unsigned)
  run: |
    ./gradlew assemble${{ steps.flavor.outputs.flavor }}Release
```

**更新为**：
```yaml
- name: Build APK (Release - debug signed)
  run: |
    ./gradlew assemble${{ steps.flavor.outputs.flavor }}Release -Pandroid.injected.signing.store.file=$ANDROID_HOME/debug.keystore -Pandroid.injected.signing.store.password=android -Pandroid.injected.signing.key.alias=androiddebugkey -Pandroid.injected.signing.key.password=android
```

### 📝 文档更新

同时更新发布说明中的 APK 描述：

**原文档** (第121-122行)：
```yaml
- **Debug版本**: 用于开发和测试，包含调试信息
- **Release版本**: 生产版本，已优化但未签名
```

**更新为**：
```yaml
- **Debug版本**: 用于开发和测试，包含调试信息，使用调试签名
- **Release版本**: 生产版本，已优化并使用调试签名
```

**原注意事项** (第129行)：
```yaml
> **注意**: Release 版本的 APK 未签名，可能需要在安装时确认安全警告
```

**更新为**：
```yaml
> **注意**: 两个版本的 APK 都使用调试签名，安装时需要启用"未知来源"
```

## 应用方法

### 方案 1: 手动修改 (推荐)

1. 打开 `.github/workflows/build-apk-release.yml` 文件
2. 找到第80行的 `Build APK (Release - unsigned)` 步骤
3. 将构建命令替换为包含调试签名参数的版本
4. 找到第121-122行的 APK 文件说明，更新描述
5. 找到第129行的注意事项，更新签名说明
6. 提交并推送更改

### 方案 2: 完整替换

```bash
# 在项目根目录执行
cp build-apk-release-updated.yml .github/workflows/build-apk-release.yml
git add .github/workflows/build-apk-release.yml
git commit -m "feat: 为 Release APK 添加调试签名支持"
git push
```

## 技术细节

### 调试签名参数说明

- `-Pandroid.injected.signing.store.file=$ANDROID_HOME/debug.keystore`: 指定调试签名证书文件
- `-Pandroid.injected.signing.store.password=android`: 调试证书密码（标准）
- `-Pandroid.injected.signing.key.alias=androiddebugkey`: 调试密钥别名（标准）
- `-Pandroid.injected.signing.key.password=android`: 调试密钥密码（标准）

### 预期效果

更新后的工作流将产生：
- ✅ **Debug APK**: 继续使用调试签名（无变化）
- ✅ **Release APK**: 现在使用调试签名，可正常安装
- ✅ **性能优化**: Release 版本仍保持代码混淆和资源压缩
- ✅ **安装体验**: 消除"未签名 APK"警告

### 版本差异

- **Debug版本**: 包含调试信息，未经优化，使用调试签名
- **Release版本**: 代码混淆和资源压缩，性能优化，使用调试签名

## 清理

应用工作流更改后，可删除这些临时文件：
```bash
git rm build-apk-release-updated.yml README_WORKFLOW_UPDATE.md
git commit -m "cleanup: 删除临时 workflow 更新文件"
git push
```

## 后续步骤

如果将来需要使用生产签名，需要：
1. 生成正式的 keystore 文件
2. 配置 GitHub Secrets 存储签名信息
3. 修改 workflow 使用生产签名参数

---

Generated with [Claude Code](https://claude.ai/code)