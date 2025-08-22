package org.fossify.calendar.models

data class DayMonthly(
    val value: Int,
    val isThisMonth: Boolean,
    val isToday: Boolean,
    val code: String,
    val weekOfYear: Int,
    var dayEvents: ArrayList<Event>,
    var indexOnMonthView: Int,
    var isWeekend: Boolean,
    // 新增农历相关字段
    var lunarDate: LunarDate? = null,
    var festivals: List<String> = emptyList(),
    var solarTerm: String? = null
)
