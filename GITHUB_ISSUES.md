# GitHub Issues 规划 - 中文日历改造

## 主要 Issue

### Issue #1: [Feature] 中文日历界面改造 - 农历显示和节日节气支持

**Issue 标题：** `[Feature] Chinese Calendar UI Redesign - Lunar Calendar and Festival Support`

**标签：** `enhancement`, `feature`, `chinese-localization`, `ui-redesign`

**描述：**
```markdown
## 概述
将当前的Fossify Calendar改造成符合中文用户习惯的日历应用，增加农历显示、节日节气标识等功能。

## 设计目标
参考提供的UI设计图，实现以下核心功能：
1. 农历日期显示（如：十二月十一）
2. 中国传统节日和现代节日标识
3. 二十四节气显示
4. 优化的中文界面设计

## 技术要求
- 基于现有MonthView.kt架构扩展
- 保持现有功能的完整性
- 支持中英文界面切换
- 性能优化，确保流畅体验

## 验收标准
- [ ] 农历日期正确显示在公历日期下方
- [ ] 节日节气有明显的视觉标识
- [ ] 界面美观，符合中文用户审美
- [ ] 功能完整，无明显Bug
- [ ] 性能良好，加载流畅

## 相关子任务
详见下方子Issues清单

## 预计工期
4-6周

## 优先级
High
```

---

## 子 Issues

### Issue #2: [Backend] 农历计算算法集成

**Issue 标题：** `[Backend] Integrate Lunar Calendar Calculation Algorithm`

**标签：** `backend`, `algorithm`, `lunar-calendar`

**描述：**
```markdown
## 目标
集成农历计算算法，实现公历与农历的准确转换。

## 技术要求
1. **算法选择**
   - 研究并选择合适的农历计算库或算法
   - 支持1900-2100年的准确转换
   - 考虑性能和准确性平衡

2. **实现要求**
   - 创建LunarCalendar.kt工具类
   - 实现公历到农历的转换方法
   - 实现农历到公历的转换方法
   - 添加农历年月日的格式化方法

3. **数据模型扩展**
   - 扩展DayMonthly模型，增加农历信息字段
   - 添加LunarDate数据类
   - 确保数据序列化兼容性

## 验收标准
- [ ] 农历转换算法准确无误
- [ ] 单元测试覆盖率 > 90%
- [ ] 性能测试通过（转换时间 < 1ms）
- [ ] 支持农历闰月处理
- [ ] 代码文档完整

## 技术参考
- 寿星万年历算法
- 香港天文台农历算法
- 考虑使用现有的Java农历库

## 预计工期
1-2周
```

### Issue #3: [Backend] 节日节气数据系统实现

**Issue 标题：** `[Backend] Chinese Festivals and Solar Terms Data System`

**标签：** `backend`, `data`, `festivals`, `solar-terms`

**描述：**
```markdown
## 目标
建立完整的中国节日节气数据系统，支持传统节日、现代节日和二十四节气。

## 功能要求
1. **节日数据管理**
   - 扩展现有的holidays数据结构
   - 添加中国传统节日（春节、中秋节、端午节等）
   - 添加现代节日（国庆节、劳动节等）
   - 支持节日类型分类

2. **节气系统**
   - 实现二十四节气计算
   - 节气数据存储和查询
   - 节气与公历日期的对应关系

3. **数据结构设计**
   - 创建ChineseFestival数据模型
   - 创建SolarTerm数据模型
   - 扩展HolidayHelper支持中文节日
   - 优化数据查询性能

## 技术实现
- 扩展app/src/main/assets/holidays/CN/目录
- 创建ChineseFestivalHelper.kt
- 创建SolarTermHelper.kt
- 更新HolidayInfo模型

## 验收标准
- [ ] 支持所有主要中国节日
- [ ] 二十四节气计算准确
- [ ] 数据查询性能优化
- [ ] 支持节日优先级排序
- [ ] 完整的数据文档

## 数据清单
**传统节日：** 春节、元宵节、清明节、端午节、七夕节、中秋节、重阳节等
**现代节日：** 元旦、妇女节、劳动节、青年节、儿童节、国庆节等
**二十四节气：** 立春、雨水、惊蛰... 大寒

## 预计工期
2-3周
```

### Issue #4: [Frontend] 月历视图UI重新设计

**Issue 标题：** `[Frontend] Monthly Calendar View UI Redesign for Chinese Style`

**标签：** `frontend`, `ui`, `chinese-style`, `month-view`

**描述：**
```markdown
## 目标
重新设计月历视图UI，实现中文日历的视觉效果和交互体验。

## 设计要求
1. **日期单元格重新设计**
   - 公历日期显示（大字体，主要位置）
   - 农历日期显示（小字体，下方位置）
   - 节日节气标识（特殊颜色或图标）
   - 今日高亮效果

2. **布局优化**
   - 调整MonthView.kt的绘制逻辑
   - 优化日期单元格的空间分配
   - 确保中文字符的良好显示效果
   - 适配不同屏幕尺寸

3. **视觉设计**
   - 节日的颜色标识系统
   - 周末日期的特殊样式
   - 当前日期的突出显示
   - 农历信息的字体和颜色

## 技术实现
- 修改MonthView.kt的onDraw方法
- 更新日期单元格的绘制逻辑
- 添加农历文本绘制
- 实现节日标识绘制
- 优化文本布局算法

## UI规范
- 公历日期：16sp，主色调
- 农历日期：10sp，次要色调
- 节日标识：红色或橙色高亮
- 节气标识：绿色或蓝色标识
- 今日：圆形背景高亮

## 验收标准
- [ ] 日期显示清晰易读
- [ ] 农历信息正确显示
- [ ] 节日节气有明显标识
- [ ] 适配各种屏幕尺寸
- [ ] 滑动和交互流畅
- [ ] 符合Material Design规范

## 预计工期
2-3周
```

### Issue #5: [Frontend] 农历日期显示组件开发

**Issue 标题：** `[Frontend] Lunar Date Display Component Development`

**标签：** `frontend`, `component`, `lunar-calendar`

**描述：**
```markdown
## 目标
开发专门的农历日期显示组件，在月历视图中显示农历信息。

## 功能要求
1. **农历日期格式化**
   - 农历日期的中文显示（初一、初二...二十九、三十）
   - 农历月份显示（正月、二月...腊月）
   - 农历年份显示（甲子年等天干地支）

2. **特殊日期处理**
   - 农历初一显示月份名称
   - 农历节日优先显示节日名称
   - 节气日期显示节气名称

3. **组件设计**
   - 创建LunarDateFormatter.kt
   - 扩展Formatter.kt添加农历格式化方法
   - 优化文本显示逻辑

## 技术实现
- 在MonthView.kt中集成农历显示
- 创建农历文本绘制方法
- 实现农历日期的缓存机制
- 优化绘制性能

## 显示规则
- 普通日期：显示农历日（如：十五）
- 农历初一：显示农历月（如：腊月）
- 传统节日：显示节日名（如：春节）
- 节气日期：显示节气名（如：立春）

## 验收标准
- [ ] 农历日期显示准确
- [ ] 文本格式美观
- [ ] 特殊日期处理正确
- [ ] 性能优化良好
- [ ] 支持多语言切换

## 预计工期
1-2周
```

### Issue #6: [Frontend] 节日节气视觉标识系统

**Issue 标题：** `[Frontend] Festival and Solar Terms Visual Indicator System`

**标签：** `frontend`, `visual-design`, `festivals`

**描述：**
```markdown
## 目标
为节日和节气设计专门的视觉标识系统，增强用户体验。

## 设计要求
1. **节日标识设计**
   - 传统节日：红色系标识
   - 现代节日：蓝色系标识
   - 国际节日：绿色系标识
   - 工作日/休息日的区分标识

2. **节气标识设计**
   - 二十四节气的专用颜色
   - 季节性的视觉区分
   - 节气名称的优雅显示

3. **视觉层次**
   - 节日优先级显示规则
   - 多个节日重叠时的处理
   - 节日与农历信息的布局协调

## 技术实现
- 创建FestivalIndicator.kt组件
- 扩展MonthView.kt的绘制方法
- 实现节日颜色主题系统
- 添加节日图标资源

## 颜色规范
- 传统节日：#FF4444（红色）
- 现代节日：#4444FF（蓝色）
- 节气：#44AA44（绿色）
- 休息日：#FF8800（橙色）

## 验收标准
- [ ] 节日标识清晰明显
- [ ] 颜色搭配协调美观
- [ ] 支持自定义颜色主题
- [ ] 适配深色/浅色模式
- [ ] 无障碍访问支持

## 预计工期
1-2周
```

### Issue #7: [Backend] 数据模型扩展和性能优化

**Issue 标题：** `[Backend] Data Model Extension and Performance Optimization`

**标签：** `backend`, `performance`, `data-model`

**描述：**
```markdown
## 目标
扩展现有数据模型以支持农历和节日信息，并优化性能。

## 技术要求
1. **数据模型扩展**
   - 扩展DayMonthly.kt模型
   - 添加农历日期字段
   - 添加节日节气信息字段
   - 保持向后兼容性

2. **缓存系统**
   - 实现农历计算结果缓存
   - 节日数据预加载和缓存
   - 优化内存使用

3. **数据库优化**
   - 评估是否需要本地数据库存储
   - 优化节日数据查询
   - 实现增量更新机制

## 实现细节
```kotlin
// 扩展DayMonthly数据模型
data class DayMonthly(
    val value: Int,
    val isThisMonth: Boolean,
    val isToday: Boolean,
    val code: String,
    val weekOfYear: Int,
    var dayEvents: ArrayList<Event>,
    var indexOnMonthView: Int,
    var isWeekend: Boolean,
    // 新增字段
    var lunarDate: LunarDate? = null,
    var festivals: List<Festival> = emptyList(),
    var solarTerm: SolarTerm? = null
)

data class LunarDate(
    val year: Int,
    val month: Int,
    val day: Int,
    val isLeapMonth: Boolean,
    val yearName: String, // 甲子年
    val monthName: String, // 腊月
    val dayName: String // 十一
)
```

## 验收标准
- [ ] 数据模型扩展完成
- [ ] 缓存系统实现
- [ ] 性能基准测试通过
- [ ] 内存使用优化
- [ ] 单元测试覆盖

## 预计工期
2周
```

### Issue #8: [Frontend] 中文界面本地化完善

**Issue 标题：** `[Frontend] Chinese Interface Localization Enhancement`

**标签：** `frontend`, `localization`, `chinese`

**描述：**
```markdown
## 目标
完善中文界面的本地化，优化中文用户体验。

## 本地化要求
1. **字符串资源完善**
   - 补充缺失的中文翻译
   - 优化现有翻译的准确性
   - 添加农历相关的新字符串

2. **字体和排版优化**
   - 优化中文字体显示效果
   - 调整文本间距和行高
   - 确保各种字号的可读性

3. **日期格式本地化**
   - 中文日期格式（2025年1月）
   - 星期显示格式（周一、周二）
   - 农历格式显示

## 新增字符串资源
```xml
<!-- 农历相关 -->
<string name="lunar_calendar">农历</string>
<string name="solar_calendar">公历</string>
<string name="lunar_month_format">%s月</string>
<string name="lunar_day_format">%s</string>

<!-- 节日相关 -->
<string name="traditional_festival">传统节日</string>
<string name="modern_festival">现代节日</string>
<string name="solar_term">节气</string>

<!-- 月份名称 -->
<string name="lunar_month_1">正月</string>
<string name="lunar_month_2">二月</string>
<!-- ... 其他月份 -->
<string name="lunar_month_12">腊月</string>
```

## 验收标准
- [ ] 所有界面文本完整中文化
- [ ] 字体显示效果良好
- [ ] 日期格式符合中文习惯
- [ ] 支持繁简体切换
- [ ] 无乱码或显示异常

## 预计工期
1周
```

### Issue #9: [Frontend] 月历视图交互优化

**Issue 标题：** `[Frontend] Monthly View Interaction Optimization`

**标签：** `frontend`, `interaction`, `ux`

**描述：**
```markdown
## 目标
优化月历视图的交互体验，增加中文日历特有的交互功能。

## 交互功能
1. **日期选择增强**
   - 点击日期显示详细信息（公历+农历）
   - 长按显示节日详情
   - 滑动切换月份的优化

2. **信息展示优化**
   - 今日详情显示优化
   - 节日信息的弹窗展示
   - 农历信息的详细视图

3. **手势交互**
   - 优化滑动手势响应
   - 添加双击快速跳转功能
   - 支持缩放手势（可选）

## 技术实现
- 扩展MonthViewWrapper.kt的触摸处理
- 优化事件分发机制
- 添加详情弹窗组件
- 实现流畅的动画效果

## 验收标准
- [ ] 交互响应流畅
- [ ] 信息展示清晰
- [ ] 手势操作符合用户习惯
- [ ] 无交互冲突
- [ ] 动画效果自然

## 预计工期
1-2周
```

### Issue #10: [Testing] 中文日历功能测试

**Issue 标题：** `[Testing] Chinese Calendar Feature Testing`

**标签：** `testing`, `qa`, `chinese-calendar`

**描述：**
```markdown
## 目标
为中文日历功能建立完整的测试体系。

## 测试范围
1. **单元测试**
   - 农历计算算法测试
   - 节日数据查询测试
   - 日期格式化测试
   - 数据模型测试

2. **UI测试**
   - 月历视图显示测试
   - 交互功能测试
   - 多语言切换测试
   - 屏幕适配测试

3. **性能测试**
   - 农历计算性能测试
   - 大量数据加载测试
   - 内存使用测试
   - 滑动流畅度测试

## 测试数据
- 准备测试用的节日数据
- 创建农历转换测试用例
- 设计边界条件测试
- 准备多年份数据验证

## 验收标准
- [ ] 单元测试覆盖率 > 85%
- [ ] UI测试自动化完成
- [ ] 性能基准达标
- [ ] 兼容性测试通过
- [ ] 用户体验测试完成

## 预计工期
1-2周
```

### Issue #11: [Documentation] 中文日历功能文档

**Issue 标题：** `[Documentation] Chinese Calendar Feature Documentation`

**标签：** `documentation`, `chinese-calendar`

**描述：**
```markdown
## 目标
为中文日历功能创建完整的技术文档和用户文档。

## 文档内容
1. **技术文档**
   - 农历算法说明
   - 架构设计文档
   - API接口文档
   - 代码注释完善

2. **用户文档**
   - 功能使用指南
   - 设置配置说明
   - 常见问题解答
   - 更新日志

3. **开发文档**
   - 代码贡献指南
   - 测试指南
   - 部署说明
   - 维护手册

## 验收标准
- [ ] 技术文档完整准确
- [ ] 用户文档易于理解
- [ ] 代码注释规范
- [ ] 文档版本同步
- [ ] 多语言文档支持

## 预计工期
1周
```

---

## Issue 依赖关系

```
Issue #1 (主要Feature)
├── Issue #2 (农历算法) [优先级：最高]
├── Issue #3 (节日数据) [依赖：Issue #2]
├── Issue #4 (UI重设计) [依赖：Issue #2, #3]
├── Issue #5 (农历组件) [依赖：Issue #2, #4]
├── Issue #6 (视觉标识) [依赖：Issue #3, #4]
├── Issue #7 (数据优化) [依赖：Issue #2, #3]
├── Issue #8 (本地化) [并行开发]
├── Issue #9 (交互优化) [依赖：Issue #4, #5, #6]
├── Issue #10 (测试) [依赖：所有功能Issues]
└── Issue #11 (文档) [最后完成]
```

## 开发时间线（预计6周）

**第1-2周：** Issues #2, #8 (农历算法 + 本地化)
**第3-4周：** Issues #3, #7 (节日数据 + 数据优化)
**第4-5周：** Issues #4, #5, #6 (UI重设计 + 组件开发)
**第5-6周：** Issues #9, #10, #11 (交互优化 + 测试 + 文档)