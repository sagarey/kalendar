package org.fossify.calendar.models

/**
 * 农历日期数据类
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
     * 获取简短的农历日期显示
     */
    fun getDisplayString(): String {
        return when {
            day == 1 -> monthName // 农历初一显示月份
            else -> dayName // 其他日期显示日期
        }
    }
}