package org.fossify.calendar.helpers

import org.fossify.calendar.models.LunarDate
import java.util.*

/**
 * 简化版农历计算器
 * 用于阶段性发布，提供基础的农历转换功能
 */
class LunarCalendarSimple {
    
    companion object {
        // 农历月份名称
        private val LUNAR_MONTHS = arrayOf("正月", "二月", "三月", "四月", "五月", "六月", 
                                          "七月", "八月", "九月", "十月", "冬月", "腊月")
        
        // 农历日期名称
        private val LUNAR_DAYS = arrayOf(
            "初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十",
            "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十",
            "廿一", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八", "廿九", "三十"
        )
        
        // 简化的农历数据（仅包含部分年份的基础数据）
        private val LUNAR_DATA_SIMPLE = mapOf(
            2025 to arrayOf(
                intArrayOf(1, 29, 29), // 2025年1月29日是农历正月初一（春节）
                intArrayOf(2, 28, 30), // 2月28日是农历二月初一
                intArrayOf(3, 29, 29), // 3月29日是农历三月初一
                intArrayOf(4, 27, 30), // 4月27日是农历四月初一
                intArrayOf(5, 27, 29), // 5月27日是农历五月初一
                intArrayOf(6, 25, 30), // 6月25日是农历六月初一
                intArrayOf(7, 25, 29), // 7月25日是农历七月初一
                intArrayOf(8, 23, 30), // 8月23日是农历八月初一
                intArrayOf(9, 22, 29), // 9月22日是农历九月初一
                intArrayOf(10, 21, 30), // 10月21日是农历十月初一
                intArrayOf(11, 20, 29), // 11月20日是农历冬月初一
                intArrayOf(12, 19, 30)  // 12月19日是农历腊月初一
            )
        )
    }
    
    /**
     * 简化的公历转农历方法
     * 仅支持2025年的基础转换
     */
    fun solarToLunar(year: Int, month: Int, day: Int): LunarDate? {
        if (year != 2025) {
            // 对于非2025年，返回一个示例农历日期用于演示
            return createDemoLunarDate(month, day)
        }
        
        val lunarData = LUNAR_DATA_SIMPLE[year] ?: return null
        
        // 简化计算：基于每月初一的日期来推算
        for (i in lunarData.indices) {
            val monthData = lunarData[i]
            val lunarMonth = i + 1
            val monthFirstDay = monthData[1] // 该农历月初一对应的公历日期
            val monthDays = monthData[2] // 该农历月的天数
            
            // 检查是否在当前农历月范围内
            if (month == monthData[0]) { // 同一个公历月
                if (day >= monthFirstDay) {
                    val lunarDay = day - monthFirstDay + 1
                    if (lunarDay <= monthDays) {
                        return LunarDate(
                            year = year,
                            month = lunarMonth,
                            day = lunarDay,
                            monthName = LUNAR_MONTHS[lunarMonth - 1],
                            dayName = LUNAR_DAYS[lunarDay - 1]
                        )
                    }
                }
            }
        }
        
        return createDemoLunarDate(month, day)
    }
    
    /**
     * 创建演示用的农历日期
     */
    private fun createDemoLunarDate(month: Int, day: Int): LunarDate {
        val lunarMonth = if (month <= 12) month else month - 12
        val lunarDay = if (day <= 30) day else day - 30
        
        return LunarDate(
            year = 2025,
            month = lunarMonth,
            day = lunarDay,
            monthName = LUNAR_MONTHS[(lunarMonth - 1).coerceIn(0, 11)],
            dayName = LUNAR_DAYS[(lunarDay - 1).coerceIn(0, 29)]
        )
    }
}