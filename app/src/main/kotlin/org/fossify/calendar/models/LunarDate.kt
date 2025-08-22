package org.fossify.calendar.models

/**
 * 农历日期数据类
 * @param year 农历年份 (如: 2025)
 * @param month 农历月份 (1-12，闰月用负数表示如-4表示闰四月)
 * @param day 农历日期 (1-30)
 * @param isLeapMonth 是否为闰月
 * @param yearName 农历年份天干地支名称 (如: 乙巳年)
 * @param monthName 农历月份中文名称 (如: 腊月)
 * @param dayName 农历日期中文名称 (如: 十一)
 */
data class LunarDate(
    val year: Int,
    val month: Int,
    val day: Int,
    val isLeapMonth: Boolean = false,
    val yearName: String = "",
    val monthName: String = "",
    val dayName: String = ""
) {
    /**
     * 获取完整的农历日期字符串
     * @return 完整农历日期 (如: 乙巳年腊月十一)
     */
    fun getFullDateString(): String {
        return "$yearName$monthName$dayName"
    }
    
    /**
     * 获取简短的农历日期显示
     * @return 简短显示 (如: 十一 或 腊月 或 春节)
     */
    fun getDisplayString(): String {
        return when {
            day == 1 -> monthName // 农历初一显示月份
            else -> dayName // 其他日期显示日期
        }
    }
    
    /**
     * 判断是否为农历新年
     */
    fun isLunarNewYear(): Boolean {
        return month == 1 && day == 1
    }
    
    /**
     * 判断是否为月初
     */
    fun isFirstDayOfMonth(): Boolean {
        return day == 1
    }
}