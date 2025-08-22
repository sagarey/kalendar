package org.fossify.calendar.models

data class LunarDate(
    val year: Int,
    val month: Int,
    val day: Int,
    val isLeapMonth: Boolean = false
) {
    fun getDisplayText(): String {
        return when (day) {
            1 -> getMonthName()
            else -> getDayName()
        }
    }
    
    private fun getMonthName(): String {
        val monthNames = arrayOf(
            "正月", "二月", "三月", "四月", "五月", "六月",
            "七月", "八月", "九月", "十月", "冬月", "腊月"
        )
        return if (month in 1..12) {
            monthNames[month - 1]
        } else {
            "${month}月"
        }
    }
    
    private fun getDayName(): String {
        return when (day) {
            in 1..10 -> {
                val dayNames = arrayOf("初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十")
                dayNames[day - 1]
            }
            in 11..19 -> {
                val numbers = arrayOf("一", "二", "三", "四", "五", "六", "七", "八", "九")
                val index = day - 11
                if (index in numbers.indices) "十${numbers[index]}" else "${day}日"
            }
            20 -> "二十"
            in 21..29 -> {
                val numbers = arrayOf("一", "二", "三", "四", "五", "六", "七", "八", "九")
                val index = day - 21
                if (index in numbers.indices) "廿${numbers[index]}" else "${day}日"
            }
            30 -> "三十"
            else -> "${day}日"
        }
    }
}