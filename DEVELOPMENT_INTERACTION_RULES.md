# 🚀 开发交互规则与最佳实践

基于Kalendar中文日历项目的开发经验，整理的完整交互规则和工作流程规范。

## 📋 核心原则

### 1. 任务管理原则
- **先规划后执行**: 复杂任务必须先创建TODO列表进行规划
- **分步交付**: 每个版本都是完整可用的功能，可独立发布
- **风险控制**: 避免大批量开发后整体回滚的风险

### 2. 质量保证原则
- **稳定性第一**: 功能实现必须确保不会导致应用崩溃
- **向后兼容**: 新功能不能影响现有功能的正常使用
- **优雅降级**: 新功能失败时不影响主要功能

## 🛠️ GitHub工作流规范

### Issue管理

#### 📝 Issue创建规则
```bash
# 1. 父Issue: 描述整体功能和规划
gh issue create --title "[PARENT] 功能名称" --body "详细描述"

# 2. 子Issue: 具体的开发任务
gh issue create --title "[CHILD] 具体任务名称" --body "技术细节"
```

#### 🏷️ Issue标签规范
- `enhancement`: 新功能开发
- `bug`: 问题修复
- `documentation`: 文档相关
- `java`/`kotlin`: 技术栈标识

#### 📊 Issue状态管理
```bash
# 查看Issue详情
gh issue view <issue_number> --repo <owner/repo>

# 添加评论更新状态
gh issue comment <issue_number> --body "状态更新内容"

# 关闭已完成的Issue
gh issue close <issue_number>
```

### 代码开发流程

#### 🌿 分支管理策略
```bash
# 1. 功能分支命名规范
git checkout -b feature/v1.x-功能描述
git checkout -b fix/问题描述
git checkout -b docs/文档更新

# 2. 分支推送
git push origin <branch_name>
```

#### 📦 提交规范
```bash
# 提交信息格式
git commit -m "类型: 简短描述

## 详细说明
- 具体修改内容
- 解决的问题
- 相关Issue引用

类型: feat|fix|docs|style|refactor|test|chore"
```

### Pull Request流程

#### 🔄 PR创建规范
```bash
# 创建PR
gh pr create --title "类型: PR标题" \
  --body "详细PR描述" \
  --head <feature_branch> \
  --base main
```

#### 📋 PR模板内容
```markdown
## 🎯 PR概述
简要描述这个PR的目的和内容

## ✨ 新增功能/修复内容
- 具体的功能点或修复点

## 🛠️ 技术实现
- 关键的技术实现细节

## 📱 用户体验
- 对用户的影响和改进

## 🧪 质量保证
- 测试覆盖情况
- 稳定性验证

## 📋 相关Issue
- Implements #xx
- Fixes #xx
- Related to #xx
```

#### ✅ PR合并规则
```bash
# 合并PR (删除分支)
gh pr merge <pr_number> --merge --delete-branch

# 查看PR状态
gh pr view <pr_number>
```

### 版本发布流程

#### 🏷️ 版本标签规范
```bash
# 版本号格式: vX.Y.Z
# X: 主版本号 (重大功能更新)
# Y: 次版本号 (新功能添加)  
# Z: 修订号 (问题修复)

# 创建版本tag
git tag v1.0.0
git push origin v1.0.0
```

#### 📦 Release创建流程
```bash
# 1. 手动创建Release (推荐)
gh release create v1.0.0 \
  --title "版本标题" \
  --notes "详细版本说明"

# 2. 推送tag触发自动构建
git push origin v1.0.0

# 3. 工作流自动添加APK文件到现有release
```

## 🔧 GitHub Actions工作流规范

### 工作流设计原则

#### 📦 APK构建工作流
- **智能检测**: 检查release是否已存在
- **条件处理**: 
  - 现有release → 只上传APK文件
  - 新release → 创建release + 上传APK
- **保护内容**: 不覆盖手动编写的release说明

#### 🛡️ 工作流安全性
```yaml
# 检查release存在性
- name: Check if release exists
  run: |
    if gh release view "$VERSION" --repo ${{ github.repository }}; then
      echo "release_exists=true"
    else
      echo "release_exists=false"  
    fi

# 条件创建release
- name: Create GitHub Release (new releases only)
  if: steps.check_release.outputs.release_exists == 'false'

# 条件上传APK
- name: Upload APK to existing release  
  if: steps.check_release.outputs.release_exists == 'true'
```

## 🐛 问题修复流程

### 问题发现和分析

#### 🔍 问题诊断步骤
1. **查看构建日志**: `gh run view <run_id> --log-failed`
2. **分析错误信息**: 定位具体的错误原因
3. **本地复现**: 在本地环境复现问题
4. **代码审查**: 检查相关代码逻辑

#### 🛠️ 修复实施流程
```bash
# 1. 创建修复分支
git checkout -b fix/问题描述

# 2. 实施修复
# 编辑相关文件...

# 3. 提交修复
git commit -m "fix: 修复描述"

# 4. 推送和创建PR
git push origin fix/问题描述
gh pr create --title "Fix: 问题描述"

# 5. 合并PR
gh pr merge <pr_number> --merge --delete-branch

# 6. 创建修复版本
git tag v1.0.x
git push origin v1.0.x
gh release create v1.0.x --title "修复版本标题"
```

### 版本递增策略

#### 🔢 版本号规则
- **v1.0.1, v1.0.2**: 小问题修复 (构建错误、编译问题)
- **v1.0.3**: 功能性修复 (算法准确性)
- **v1.0.4**: 用户体验修复 (显示问题)
- **v1.1.0**: 新功能版本 (节日标识)

## 📱 用户反馈处理

### 反馈收集渠道
- **GitHub Issues**: 功能请求和问题报告
- **Release评论**: 版本使用反馈
- **直接沟通**: 实时问题反馈

### 响应流程
```bash
# 1. 问题确认
gh issue create --title "用户反馈: 问题描述"

# 2. 快速修复
git checkout -b fix/用户反馈问题
# 实施修复...
git commit -m "fix: 解决用户反馈问题"

# 3. 紧急发布
git tag v1.0.x
gh release create v1.0.x --title "紧急修复版本"

# 4. 用户通知
gh issue comment <issue_number> --body "问题已在v1.0.x中修复"
```

## 🧪 测试和验证规范

### 代码质量检查
```bash
# 1. 单元测试 (如果可用)
./gradlew test

# 2. 构建验证
./gradlew assembleDebug

# 3. 静态分析
# 检查代码逻辑和潜在问题
```

### 功能验证清单
- [ ] 新功能按预期工作
- [ ] 不影响现有功能
- [ ] 异常情况处理正确
- [ ] 性能在可接受范围内
- [ ] 用户界面显示正常

## 📚 文档和沟通规范

### Issue文档化
```markdown
## 🎯 目标
明确的功能目标描述

## 🔧 技术要求
具体的技术实现要求

## ✅ 验收标准
明确的完成标准

## 🔄 依赖关系
与其他Issue的依赖关系

## ⏱️ 预计工期
合理的时间估算
```

### PR文档化
```markdown
## 🎯 PR概述
## ✨ 新增功能
## 🛠️ 技术实现  
## 📱 用户体验
## 🧪 质量保证
## 📋 相关Issue
```

### Release文档化
```markdown
## 🌟 版本亮点
## ✨ 新功能特性
## 🔧 问题修复
## 📱 用户体验改进
## 🛠️ 技术改进
## 📋 相关Issue
## 🚀 下一步规划
```

## 🎯 分步交付策略

### 版本规划原则
1. **MVP优先**: 每个版本都是最小可用产品
2. **用户价值**: 每个版本都为用户提供实际价值
3. **风险分散**: 避免大功能一次性交付
4. **反馈循环**: 每个版本后收集用户反馈

### 交付节奏
```
V1.0: 基础功能 (2周)
V1.1: 增强功能 (2周)  
V1.2: 优化功能 (1周)
V1.3: 完善功能 (1周)
V1.4: 正式版本 (1周)
```

## 🚨 应急响应流程

### 紧急问题处理
```bash
# 1. 立即响应 (< 30分钟)
gh issue create --title "URGENT: 问题描述"

# 2. 快速修复 (< 2小时)
git checkout -b hotfix/紧急修复
# 实施最小修复...
git commit -m "hotfix: 紧急修复"

# 3. 紧急发布 (< 4小时)
gh pr create --title "Hotfix: 紧急修复"
gh pr merge <pr_number> --merge
git tag v1.0.x
gh release create v1.0.x

# 4. 用户通知
gh issue comment --body "问题已紧急修复"
```

## 🔄 持续改进

### 工作流优化
- **根据问题调整**: 发现问题立即优化工作流
- **用户反馈驱动**: 基于用户反馈改进流程
- **自动化增强**: 逐步增加自动化程度

### 规则演进
- **记录经验**: 将成功的做法固化为规则
- **学习改进**: 从问题中学习并更新规则
- **团队同步**: 确保所有参与者遵循相同规则

---

## 📖 快速参考

### 常用命令
```bash
# Issue管理
gh issue list --repo <owner/repo>
gh issue view <number> --repo <owner/repo>
gh issue create --title "标题" --body "内容"
gh issue comment <number> --body "评论"

# PR管理  
gh pr create --title "标题" --body "内容"
gh pr view <number>
gh pr merge <number> --merge --delete-branch

# Release管理
gh release create <tag> --title "标题" --notes "说明"
gh release view <tag>
gh release upload <tag> <file>

# 构建管理
gh run list --limit 5
gh run view <run_id>
gh run view <run_id> --log-failed
```

### 项目结构
```
.github/workflows/     # GitHub Actions工作流
app/src/main/kotlin/   # 主要代码
app/src/test/kotlin/   # 单元测试
app/src/main/res/      # 资源文件
  values-zh-rCN/       # 中文本地化
  values/              # 默认资源
```

## 🎯 具体交互规则总结

### 基于我们项目的实际规则

#### 1. 📋 项目规划阶段
```bash
# 使用gh命令查看和分析Issue
gh issue view 29 --repo sagarey/kalendar
gh issue list --repo sagarey/kalendar --state all

# 创建详细的分步交付计划
# 更新父Issue包含完整规划
gh issue edit 29 --body-file plan.md
```

#### 2. 🔄 开发实施阶段
```bash
# 创建功能分支
git checkout -b feature/v1.0-basic-lunar-display

# 逐步实现功能
# - 数据模型扩展
# - 算法实现  
# - UI集成
# - 测试添加

# 提交完整功能
git commit -m "feat: V1.0 - 完整功能描述"
```

#### 3. 📦 PR和合并流程
```bash
# 创建详细的PR
gh pr create --title "V1.0 Release: 功能名称" \
  --body "详细的PR描述"

# 直接合并 (对于明确的功能)
gh pr merge <pr_number> --merge --delete-branch
```

#### 4. 🚀 版本发布流程
```bash
# 手动创建Release (包含详细说明)
gh release create v1.0.0 \
  --title "版本标题" \
  --notes "详细版本说明"

# 工作流自动添加APK (不覆盖说明)
# 由推送tag触发
```

#### 5. 🐛 问题响应流程
```bash
# 快速问题分析
gh run view <run_id> --log-failed

# 立即修复实施
git commit -m "fix: 具体问题修复"
git push origin main

# 紧急版本发布
git tag v1.0.x
gh release create v1.0.x --title "修复版本"
```

### 🔑 关键经验教训

#### ✅ 成功实践
- **分步交付**: V1.0分为多个小版本，降低风险
- **快速响应**: 发现问题立即修复发布
- **工作流保护**: 保护手动release说明不被覆盖
- **用户反馈**: 基于实际使用反馈快速迭代

#### ❌ 避免的问题
- **大批量开发**: 避免一次性开发太多功能
- **忽视稳定性**: 功能实现必须考虑稳定性
- **算法复杂性**: 避免过度复杂的算法实现
- **缺少异常处理**: 必须添加充分的错误处理

### 📊 版本发布节奏
```
v1.0.0: 初始版本 → v1.0.1: 构建修复 → v1.0.2: 闪退修复 
→ v1.0.3: 准确性改进 → v1.0.4: 月份修复
```

每个版本都解决了具体问题，确保用户始终有可用的版本。

### 🎭 角色分工
- **规划者**: 分析需求，制定计划，更新Issue
- **开发者**: 实现功能，编写测试，提交代码
- **发布者**: 创建release，管理版本，响应反馈
- **维护者**: 监控构建，修复问题，优化工作流

这套规则确保了高效、稳定、可追踪的开发流程，适用于类似的开源项目开发。