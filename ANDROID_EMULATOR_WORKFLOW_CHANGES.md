# Android Emulator Testing Workflow 修改说明

## 修改概述

对 `.github/workflows/android-emulator-testing.yml` 进行了以下重要修改，以支持在向 main 分支推送 PR 时自动触发测试，并在权限不足时使用 GH_TOKEN 环境变量进行推送。

## 主要修改内容

### 1. 触发条件调整

**新增触发条件：**
```yaml
on:
  workflow_dispatch:
  pull_request:
    branches:
      - main
    types: [opened, synchronize, reopened]
```

- 保留了原有的手动触发 (`workflow_dispatch`)
- 新增了在向 main 分支推送 PR 时的自动触发
- 支持 PR 的打开、同步和重新打开事件

### 2. 权限配置增强

**新增权限：**
```yaml
permissions:
  contents: read
  pages: write
  id-token: write
  pull-requests: write  # 新增
```

### 3. 权限检查和备用推送机制

**新增 job: `check-permissions-and-push`**

这个 job 会：
- 检查是否有直接推送权限
- 如果权限不足，自动安装 GitHub CLI
- 使用 GH_TOKEN 环境变量进行身份验证
- 通过 gh 命令行推送仓库改动

**关键功能：**
- 自动检测推送权限
- 使用 `GH_TOKEN` 作为备用认证方式
- 支持通过 token 进行 git 操作

### 4. 自动创建测试结果 PR

**新增 job: `auto-create-pr`**

这个 job 会：
- 在测试完成后自动创建新的分支
- 生成测试结果摘要文档
- 自动创建 PR 来展示测试结果

**PR 内容包含：**
- 测试信息（时间、触发 PR、分支等）
- 测试状态（构建、安装、启动、AI 分析等）
- 详细报告链接（GitHub Pages）
- 测试环境信息

## 环境变量要求

### 必需的 Secrets

1. **`GH_TOKEN`** (推荐)
   - 用于在权限不足时进行推送操作
   - 需要 `repo` 权限
   - 如果未设置，会回退到 `GITHUB_TOKEN`

2. **`CURSOR_API_KEY`** (现有)
   - 用于 AI 分析功能

3. **`GITHUB_TOKEN`** (自动提供)
   - GitHub Actions 自动提供的 token
   - 权限有限，主要用于基本操作

## 工作流程

1. **PR 创建/更新** → 触发工作流
2. **权限检查** → 确定是否需要使用 GH_TOKEN
3. **Android 模拟器测试** → 构建、安装、测试应用
4. **AI 分析** → 生成测试报告
5. **发布报告** → 部署到 GitHub Pages
6. **创建结果 PR** → 自动创建包含测试结果的 PR

## 注意事项

1. **GH_TOKEN 设置**：
   - 建议在仓库 Settings → Secrets and variables → Actions 中设置 `GH_TOKEN`
   - Token 需要 `repo` 权限以支持推送操作

2. **权限回退机制**：
   - 如果 `GH_TOKEN` 未设置，会自动使用 `GITHUB_TOKEN`
   - 但 `GITHUB_TOKEN` 权限有限，可能无法完成所有操作

3. **测试结果 PR**：
   - 每次测试都会创建独立的 PR
   - PR 标题包含时间戳，避免冲突
   - 包含完整的测试信息和报告链接

## 兼容性

- 保持与现有手动触发功能的完全兼容
- 不影响现有的测试流程和报告生成
- 新增功能为可选，不影响原有功能

## 故障排除

如果遇到权限问题：
1. 检查 `GH_TOKEN` 是否正确设置
2. 确认 token 具有足够的权限
3. 查看工作流日志中的权限检查步骤
4. 必要时可以手动触发工作流进行测试