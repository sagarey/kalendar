# GitHub Issue: 重新组织菜单栏布局

## Issue 标题
将设置按钮移动到"更多"下拉菜单中，放置在"关于"菜单项上方

## Issue 类型
- [x] Enhancement (功能增强)
- [ ] Bug Report
- [ ] Feature Request

## 问题描述

### 当前状态
根据当前的菜单配置 (`app/src/main/res/menu/menu_main.xml`)，菜单栏的布局如下：

**工具栏按钮 (showAsAction="always/ifRoom"):**
- 今日 (go_to_today) - always
- 切换视图 (change_view) - always  
- 筛选 (filter) - ifRoom
- 刷新CalDAV日历 (refresh_caldav_calendars) - ifRoom
- **设置 (settings) - ifRoom** ← 需要移动的按钮
- 关于 (about) - ifRoom
- 搜索 (search) - always
- 更多Fossify应用 (more_apps_from_us) - ifRoom

**下拉菜单项 (showAsAction="never"):**
- 跳转到日期 (go_to_date)
- 打印 (print)
- 添加节假日 (add_holidays)
- 添加生日 (add_birthdays)
- 添加纪念日 (add_anniversaries)

### 期望的改动

将**设置 (settings)**按钮从工具栏移动到"更多"下拉菜单中，并放置在**关于 (about)**菜单项的上方。

### 具体实现方案

需要修改 `app/src/main/res/menu/menu_main.xml` 文件：

1. 将设置菜单项的 `app:showAsAction` 从 `"ifRoom"` 改为 `"never"`
2. 调整菜单项的顺序，将设置项移动到关于项之前

### 修改后的菜单结构

**工具栏按钮:**
- 今日 (go_to_today) - always
- 切换视图 (change_view) - always  
- 筛选 (filter) - ifRoom
- 刷新CalDAV日历 (refresh_caldav_calendars) - ifRoom
- 关于 (about) - ifRoom
- 搜索 (search) - always
- 更多Fossify应用 (more_apps_from_us) - ifRoom

**下拉菜单项 (按显示顺序):**
- 跳转到日期 (go_to_date)
- 打印 (print)
- 添加节假日 (add_holidays)
- 添加生日 (add_birthdays)
- 添加纪念日 (add_anniversaries)
- **设置 (settings)** ← 新位置
- 关于 (about) - 如果空间不足时会显示在这里

### 受影响的文件

- `app/src/main/res/menu/menu_main.xml` - 主要修改文件
- `app/src/main/kotlin/org/fossify/calendar/activities/MainActivity.kt` - 菜单点击处理逻辑 (无需修改)

### 用户体验改进

这个改动将：
1. 减少工具栏的视觉混乱
2. 为更重要的功能按钮提供更多空间
3. 将设置功能放在逻辑上更合适的"更多选项"区域
4. 保持设置功能易于访问

### 优先级
- [ ] 低
- [x] 中
- [ ] 高
- [ ] 紧急

### 标签建议
- `enhancement`
- `ui/ux`
- `menu`
- `good first issue`

---

## 技术实现细节

### 当前菜单项配置
```xml
<item
    android:id="@+id/settings"
    android:icon="@drawable/ic_settings_cog_vector"
    android:title="@string/settings"
    app:showAsAction="ifRoom" />
<item
    android:id="@+id/about"
    android:icon="@drawable/ic_info_vector"
    android:title="@string/about"
    app:showAsAction="ifRoom" />
```

### 建议的修改
```xml
<item
    android:id="@+id/about"
    android:icon="@drawable/ic_info_vector"
    android:title="@string/about"
    app:showAsAction="ifRoom" />
    
<!-- 设置项移动到下拉菜单，放在关于项之前 -->
<item
    android:id="@+id/settings"
    android:icon="@drawable/ic_settings_cog_vector"
    android:title="@string/settings"
    app:showAsAction="never" />
```

### 验证方法
1. 编译并运行应用
2. 检查工具栏是否不再显示设置按钮
3. 点击"更多"按钮（三个点）
4. 验证设置选项出现在关于选项上方
5. 测试设置菜单功能是否正常工作