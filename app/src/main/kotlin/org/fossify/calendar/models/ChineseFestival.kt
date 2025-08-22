package org.fossify.calendar.models

/**
 * 中国节日数据模型
 * @param name 节日名称
 * @param type 节日类型
 * @param isLunar 是否为农历节日
 * @param month 月份
 * @param day 日期
 * @param priority 显示优先级（数值越小优先级越高）
 * @param color 节日标识颜色
 * @param description 节日描述
 */
data class ChineseFestival(
    val name: String,
    val type: FestivalType,
    val isLunar: Boolean = false,
    val month: Int,
    val day: Int,
    val priority: Int = 5,
    val color: String = "",
    val description: String = ""
) {
    /**
     * 获取节日的显示名称
     */
    fun getDisplayName(): String = name
    
    /**
     * 判断是否为传统节日
     */
    fun isTraditionalFestival(): Boolean = type == FestivalType.TRADITIONAL
    
    /**
     * 判断是否为现代节日
     */
    fun isModernFestival(): Boolean = type == FestivalType.MODERN
    
    /**
     * 判断是否为国际节日
     */
    fun isInternationalFestival(): Boolean = type == FestivalType.INTERNATIONAL
}

/**
 * 节日类型枚举
 */
enum class FestivalType {
    TRADITIONAL,    // 传统节日
    MODERN,         // 现代节日
    INTERNATIONAL,  // 国际节日
    SOLAR_TERM     // 节气
}