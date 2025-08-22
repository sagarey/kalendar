package org.fossify.calendar.helpers

import org.fossify.calendar.models.LunarDate
import org.joda.time.DateTime

/**
 * 简化的农历计算工具类
 * 基于算法实现公历到农历的转换
 * 支持1900-2100年的基本转换
 */
object LunarCalendarSimple {
    
    // 农历数据表：每年12或13个月的天数
    // 数据格式：bit0-11代表12个月，bit12代表闰月天数，bit13-16代表闰月月份
    private val lunarInfo = intArrayOf(
        0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
        0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,
        0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
        0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950,
        0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557,
        0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0,
        0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0,
        0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
        0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570,
        0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,
        0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5,
        0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930,
        0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
        0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,
        0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0,
        0x14b63, 0x09370, 0x049f8, 0x04970, 0x064b0, 0x168a6, 0x0ea50, 0x06b20, 0x1a6c4, 0x0aae0,
        0x0a2e0, 0x0d2e3, 0x0c960, 0x0d557, 0x0d4a0, 0x0da50, 0x05d55, 0x056a0, 0x0a6d0, 0x055d4,
        0x052d0, 0x0a9b8, 0x0a950, 0x0b4a0, 0x0b6a6, 0x0ad50, 0x055a0, 0x0aba4, 0x0a5b0, 0x052b0,
        0x0b273, 0x06930, 0x07337, 0x06aa0, 0x0ad50, 0x14b55, 0x04b60, 0x0a570, 0x054e4, 0x0d160,
        0x0e968, 0x0d520, 0x0daa0, 0x16aa6, 0x056d0, 0x04ae0, 0x0a9d4, 0x0a2d0, 0x0d150, 0x0f252
    )
    
    // 农历基准日期：1900年1月31日对应农历1900年正月初一
    private val baseDate = DateTime(1900, 1, 31, 0, 0, 0)
    
    /**
     * 将公历日期转换为农历日期
     */
    fun solarToLunar(solarDate: DateTime): LunarDate? {
        return try {
            val dayOffset = calculateDayOffset(solarDate)
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
        var tempOffset = offset
        var lunarYear = 1900
        var lunarMonth = 1
        var lunarDay = 1
        var isLeapMonth = false
        
        // 计算农历年份
        while (tempOffset > 0) {
            val daysInYear = getDaysInLunarYear(lunarYear)
            if (tempOffset >= daysInYear) {
                tempOffset -= daysInYear
                lunarYear++
            } else {
                break
            }
        }
        
        // 计算农历月份和日期
        if (tempOffset > 0) {
            val monthDays = getMonthDaysArray(lunarYear)
            for (month in monthDays.indices) {
                if (tempOffset >= monthDays[month]) {
                    tempOffset -= monthDays[month]
                    lunarMonth++
                } else {
                    lunarDay = tempOffset + 1
                    break
                }
            }
        }
        
        return LunarDate(lunarYear, lunarMonth, lunarDay, isLeapMonth)
    }
    
    /**
     * 获取农历年份的总天数
     */
    private fun getDaysInLunarYear(year: Int): Int {
        if (year < 1900 || year > 2100) return 365
        
        val info = lunarInfo[year - 1900]
        var sum = 348 // 12个小月的天数
        
        // 计算大月的额外天数
        for (i in 0x8000 downTo 0x8) {
            if (info and i != 0) {
                sum += 1
            }
        }
        
        // 加上闰月天数
        if (getLeapMonth(year) != 0) {
            sum += if (info and 0x10000 != 0) 30 else 29
        }
        
        return sum
    }
    
    /**
     * 获取闰月月份
     */
    private fun getLeapMonth(year: Int): Int {
        if (year < 1900 || year > 2100) return 0
        return lunarInfo[year - 1900] and 0xf
    }
    
    /**
     * 获取每个月的天数数组
     */
    private fun getMonthDaysArray(year: Int): IntArray {
        if (year < 1900 || year > 2100) {
            // 返回默认的12个月，每月29天
            return IntArray(12) { 29 }
        }
        
        val info = lunarInfo[year - 1900]
        val monthDays = IntArray(12)
        
        for (i in 0..11) {
            monthDays[i] = if (info and (0x10000 shr i) != 0) 30 else 29
        }
        
        return monthDays
    }
}