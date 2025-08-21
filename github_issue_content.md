## GitHub Issue 内容

**仓库:** sagarey/kalendar  
**标题:** 头部菜单栏：确保「关于」菜单项保持在右侧更多下拉列表的底部位置  
**标签:** enhancement, ui/ux  

---

## 需求描述

需要确保头部菜单栏中的「关于」(About) 菜单项始终位于右侧更多下拉列表的最底部位置，与之前的行为保持一致。

## 当前状态

在 `app/src/main/res/menu/menu_main.xml` 文件中，「关于」菜单项当前配置为：

```xml
<item
    android:id="@+id/about"
    android:icon="@drawable/ic_info_vector"
    android:title="@string/about"
    app:showAsAction="ifRoom" />
```

## 期望行为

- 「关于」菜单项应该始终显示在右侧更多下拉菜单的底部
- 确保菜单项顺序的一致性和用户体验的连续性
- 避免因屏幕尺寸或其他因素导致的菜单项位置变化

## 技术细节

相关文件：
- `app/src/main/res/menu/menu_main.xml` (第52-55行)
- `app/src/main/kotlin/org/fossify/calendar/activities/MainActivity.kt` (菜单处理逻辑)

当前菜单项在XML中的顺序：
1. go_to_today (always)
2. change_view (always) 
3. filter (ifRoom)
4. refresh_caldav_calendars (ifRoom)
5. go_to_date (never)
6. print (never)
7. add_holidays (never)
8. add_birthdays (never)
9. add_anniversaries (never)
10. settings (never)
11. **about (ifRoom)** ← 当前位置
12. more_apps_from_us (ifRoom)

## 建议解决方案

将「关于」菜单项的 `showAsAction` 属性改为 `never`，确保它始终出现在下拉菜单中，并调整菜单项在XML中的顺序，使其位于下拉菜单的底部。

可能的实现方案：

1. **方案一：** 将 `about` 菜单项移动到XML文件的最后位置，并设置 `app:showAsAction="never"`
2. **方案二：** 保持当前位置，但将 `showAsAction` 改为 `never`，确保始终在下拉菜单中显示

## 优先级

中等 - 用户体验一致性改进

## 备注

此需求为功能改进需求，需要确保用户界面的一致性和可预测性。

---

**创建说明:** 请将以上内容复制到 GitHub Issues 页面创建新的 issue。