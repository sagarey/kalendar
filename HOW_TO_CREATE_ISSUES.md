# 📋 如何使用这些问题报告创建 GitHub Issues

## 🎯 概述

本文档包含了对 Kalendar v1.0.0 的详细验收评估，以及发现的关键问题。由于无法直接通过 API 创建 Issues（需要认证），请按照以下步骤手动创建。

## 📁 文件结构

```
/workspace/
├── ACCEPTANCE_EVALUATION_REPORT.md     # 完整的验收评估报告
├── github_issues/                      # GitHub Issues 模板
│   ├── issue_1_lunar_display_missing.md
│   ├── issue_2_algorithm_incomplete.md
│   └── issue_3_test_coverage_insufficient.md
└── HOW_TO_CREATE_ISSUES.md            # 本文档
```

## 🚀 创建 Issues 步骤

### 方法1: 使用 GitHub CLI (推荐)

如果您有 GitHub CLI 并已认证：

```bash
# 安装 GitHub CLI (如果尚未安装)
# 参考: https://cli.github.com/

# 认证
gh auth login

# 创建 Issue 1: 农历显示缺失
gh issue create \
  --repo sagarey/kalendar \
  --title "🚨 农历信息在月历视图中完全缺失" \
  --body-file github_issues/issue_1_lunar_display_missing.md \
  --label "bug,ui,high-priority,lunar-calendar"

# 创建 Issue 2: 算法不完整  
gh issue create \
  --repo sagarey/kalendar \
  --title "🔧 农历计算算法数据表不完整" \
  --body-file github_issues/issue_2_algorithm_incomplete.md \
  --label "enhancement,algorithm,medium-priority,lunar-calendar"

# 创建 Issue 3: 测试覆盖不足
gh issue create \
  --repo sagarey/kalendar \
  --title "📝 单元测试覆盖不足，无法确保农历转换准确性" \
  --body-file github_issues/issue_3_test_coverage_insufficient.md \
  --label "testing,quality-assurance,medium-priority,lunar-calendar"
```

### 方法2: 通过 GitHub Web 界面

1. **访问仓库**: https://github.com/sagarey/kalendar
2. **点击 "Issues" 标签页**
3. **点击 "New issue" 按钮**
4. **复制粘贴对应的问题内容**

#### Issue 1 设置:
- **标题**: `🚨 农历信息在月历视图中完全缺失`
- **内容**: 复制 `github_issues/issue_1_lunar_display_missing.md` 的全部内容
- **标签**: `bug`, `ui`, `high-priority`, `lunar-calendar`
- **里程碑**: `v1.0.1 Hotfix` (如果存在)

#### Issue 2 设置:
- **标题**: `🔧 农历计算算法数据表不完整`
- **内容**: 复制 `github_issues/issue_2_algorithm_incomplete.md` 的全部内容  
- **标签**: `enhancement`, `algorithm`, `medium-priority`, `lunar-calendar`
- **里程碑**: `v1.0.1` (如果存在)

#### Issue 3 设置:
- **标题**: `📝 单元测试覆盖不足，无法确保农历转换准确性`
- **内容**: 复制 `github_issues/issue_3_test_coverage_insufficient.md` 的全部内容
- **标签**: `testing`, `quality-assurance`, `medium-priority`, `lunar-calendar`
- **里程碑**: `v1.0.2` (如果存在)

## 🏷️ 建议的标签体系

为了更好地管理这些问题，建议创建以下标签：

### 功能标签
- `lunar-calendar` - 农历相关功能
- `ui` - 用户界面问题
- `algorithm` - 算法相关
- `testing` - 测试相关

### 优先级标签  
- `high-priority` - 高优先级，需要立即处理
- `medium-priority` - 中优先级，计划处理
- `low-priority` - 低优先级，有时间时处理

### 类型标签
- `bug` - 功能缺陷
- `enhancement` - 功能增强
- `quality-assurance` - 质量保证
- `technical-debt` - 技术债务

## 📊 问题优先级建议

### 🔴 P0 - 立即修复 (建议 v1.0.1 Hotfix)
1. **Issue #1: 农历显示缺失** - 核心功能完全失效

### 🟠 P1 - 短期修复 (建议 v1.0.1)  
2. **Issue #2: 算法数据不完整** - 影响功能准确性

### 🟡 P2 - 中期改进 (建议 v1.0.2)
3. **Issue #3: 测试覆盖不足** - 质量保证问题

## 🔄 后续跟踪建议

### 创建里程碑
建议在 GitHub 中创建以下里程碑：
- `v1.0.1 Hotfix` - 紧急修复农历显示问题
- `v1.0.1` - 完善农历功能
- `v1.0.2` - 质量改进和测试完善

### 项目看板
可以创建 GitHub Project 看板来跟踪进度：
- **To Do** - 待处理的问题
- **In Progress** - 正在处理的问题  
- **Review** - 等待代码审查的问题
- **Done** - 已完成的问题

### 定期评估
建议每周评估一次问题处理进度，确保：
- 高优先级问题得到及时处理
- 修复质量符合验收标准
- 用户反馈得到及时响应

## 📞 联系信息

如果在创建 Issues 过程中遇到问题，可以：
1. 查看 GitHub 官方文档: https://docs.github.com/en/issues
2. 参考 GitHub CLI 文档: https://cli.github.com/manual/
3. 联系项目维护者获取帮助

---

**注意**: 请确保在创建 Issues 时保持专业和建设性的语调，重点关注问题的技术细节和解决方案，而不是指责或抱怨。