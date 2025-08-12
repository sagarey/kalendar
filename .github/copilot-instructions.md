
# Fossify Calendar Copilot 协作说明（中文）

## 项目概览

- **Fossify Calendar** 是一款注重隐私的开源安卓日历应用。
- 代码基于 Kotlin，广泛使用 AndroidX、Room、Jetpack 等库。
- 主要功能：事件/任务管理、提醒、桌面小部件、CalDAV 同步、节假日导入、丰富的日/周/月/年视图。

## 架构与核心模块

- **主代码目录**：`app/src/main/kotlin/org/fossify/calendar/`
  - `activities/`：主界面与入口（如 `MainActivity.kt`、`EventActivity.kt`）
  - `fragments/`：各类日历视图的 UI 片段
  - `models/`：数据模型（如 `Event`、`EventType`、`Task`、`Widget`）
  - `databases/`：Room 数据库（`EventsDatabase.kt`）及 DAO
  - `helpers/`：业务逻辑、CalDAV 同步（`CalDAVHelper.kt`）、格式化、工具类
  - `services/`：安卓后台服务（如提醒、小部件）
  - `adapters/`：RecyclerView 及小部件适配器
  - `extensions/`：Kotlin 扩展函数（模型、视图、工具等）

- **数据流**：Activity/Fragment 通过 DAO 和 helper 操作 Room 数据库。CalDAV 同步、节假日导入由专门 helper/service 处理。

- **小部件支持**：多种小部件配置 Activity 和服务。

## 构建、测试与部署

- **构建**：使用 Gradle（`./gradlew assembleDebug` 或 `assembleRelease`）。签名配置通过 `keystore.properties` 或环境变量加载。
- **静态分析**：运行 `./gradlew detekt` 进行代码检查。
- **Fastlane**：用于部署和元数据管理，详见 `fastlane/README.md`：
  - `fastlane android test` — 运行单元/仪器测试（如有）
  - `fastlane android deploy` — 构建并部署到 Google Play
  - `fastlane android metadata` — 推送商店元数据

## 项目约定与开发模式

- **Room 实体**：所有持久化数据（事件、任务、小部件）均为 Room 实体，定义于 `models/` 并在 `EventsDatabase.kt` 注册。
- **Kotlin 扩展**：通用逻辑优先用扩展函数实现，集中于 `extensions/`。
- **CalDAV/外部同步**：由 `helpers/CalDAVHelper.kt` 及相关 job/service 管理。
- **UI**：遵循 Material Design，支持动态主题和自定义小部件。
- **未检测到测试代码**：如需测试请在 `app/src/test/` 或 `app/src/androidTest/` 下添加。

## 集成点

- **CalDAV**：同步逻辑见 `helpers/CalDAVHelper.kt`、`jobs/CalDAVUpdateListener.kt`
- **节假日导入**：数据在 `assets/holidays/`，逻辑见 `helpers/HolidayHelper.kt`
- **小部件更新**：由 `WidgetService.kt` 及相关 provider 管理

## 示例

- 新增事件类型：需在 `models/EventType.kt`、DAO 及相关 UI/适配器中同步更新
- 新增小部件：需在 `services/` 实现服务、`activities/` 实现配置 Activity，并更新 manifest

## 参考文件

- 主入口：`MainActivity.kt`
- 数据库：`databases/EventsDatabase.kt`
- 构建配置：`app/build.gradle.kts`、`gradle/libs.versions.toml`
- Fastlane：`fastlane/README.md`

---

如有不清楚或需补充之处，请反馈！
