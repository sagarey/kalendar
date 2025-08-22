package org.fossify.calendar.helpers

import org.fossify.calendar.models.LunarDate
import org.joda.time.DateTime

/**
 * 简化的农历计算工具类
 * 使用简单安全的近似算法
 * 避免复杂计算导致的崩溃问题
 */
object LunarCalendarSimple {
    
    // 简化的农历基准日期
    private val baseDate = DateTime(1900, 1, 31, 0, 0, 0)
    
    /**
     * 将公历日期转换为农历日期
     */
    fun solarToLunar(solarDate: DateTime): LunarDate? {
        return try {
            // 添加额外的安全检查
            if (solarDate.year < 1900 || solarDate.year > 2100) {
                return null
            }
            
            val dayOffset = calculateDayOffset(solarDate)
            if (dayOffset < 0) {
                return null
            }
            
            convertOffsetToLunar(dayOffset)
        } catch (e: Exception) {
            null // 如果计算失败，返回null以优雅降级
        }
    }
    
    /**
     * 计算给定日期与基准日期的天数差
     */
    private fun calculateDayOffset(date: DateTime): Int {
        val days = org.joda.time.Days.daysBetween(baseDate, date).days
        return days
    }
    
    /**
     * 将天数偏移转换为农历日期
     */
    private fun convertOffsetToLunar(offset: Int): LunarDate {
        // 简化实现：使用近似算法避免复杂计算导致的崩溃
        val lunarYear = 1900 + (offset / 365)
        val remainingDays = offset % 365
        val lunarMonth = (remainingDays / 30) + 1
        val lunarDay = (remainingDays % 30) + 1
        
        // 确保值在合理范围内
        val safeYear = if (lunarYear in 1900..2100) lunarYear else 2025
        val safeMonth = if (lunarMonth in 1..12) lunarMonth else 1
        val safeDay = if (lunarDay in 1..30) lunarDay else 1
        
        return LunarDate(safeYear, safeMonth, safeDay, false)
    }

}