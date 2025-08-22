package org.fossify.calendar.helpers

import org.fossify.calendar.models.LunarDate
import java.util.*

/**
 * 农历计算工具类
 * 基于寿星万年历算法实现公历到农历的转换
 * 支持1900-2100年的准确转换
 */
class LunarCalendar {
    
    companion object {
        // 农历数据：每年的月份天数信息，从1900年开始
        // 数据格式：高4位表示闰月月份，低12位表示12个月的大小月（1为30天，0为29天）
        private val LUNAR_INFO = longArrayOf(
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
            0x0e968, 0x0d520, 0x0daa0, 0x16aa6, 0x056d0, 0x04ae0, 0x0a9d4, 0x0a2d0, 0x0d150, 0x0f252,
            0x0d520
        )
        
        // 天干
        private val HEAVENLY_STEMS = arrayOf("甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸")
        
        // 地支
        private val EARTHLY_BRANCHES = arrayOf("子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥")
        
        // 农历月份名称
        private val LUNAR_MONTHS = arrayOf("正月", "二月", "三月", "四月", "五月", "六月", 
                                          "七月", "八月", "九月", "十月", "冬月", "腊月")
        
        // 农历日期名称
        private val LUNAR_DAYS = arrayOf(
            "初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十",
            "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十",
            "廿一", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八", "廿九", "三十"
        )
        
        // 1900年1月31日对应农历1900年1月1日
        private const val BASE_YEAR = 1900
        private val BASE_DATE = Calendar.getInstance().apply {
            set(1900, 0, 31) // 1900年1月31日
        }
    }
    
    /**
     * 将公历日期转换为农历日期
     * @param year 公历年份
     * @param month 公历月份 (1-12)
     * @param day 公历日期
     * @return LunarDate 农历日期对象
     */
    fun solarToLunar(year: Int, month: Int, day: Int): LunarDate {
        if (year < 1900 || year > 2100) {
            throw IllegalArgumentException("年份超出支持范围 (1900-2100)")
        }
        
        // 计算距离基准日期的天数
        val solarDate = Calendar.getInstance().apply {
            set(year, month - 1, day)
        }
        val daysDiff = ((solarDate.timeInMillis - BASE_DATE.timeInMillis) / (24 * 60 * 60 * 1000)).toInt()
        
        // 从1900年开始逐年计算
        var lunarYear = BASE_YEAR
        var lunarMonth = 1
        var lunarDay = 1
        var remainingDays = daysDiff
        
        // 计算年份
        while (remainingDays > 0) {
            val yearDays = getLunarYearDays(lunarYear)
            if (remainingDays >= yearDays) {
                remainingDays -= yearDays
                lunarYear++
            } else {
                break
            }
        }
        
        // 计算月份和日期
        var isLeapMonth = false
        val leapMonth = getLeapMonth(lunarYear)
        
        while (remainingDays > 0) {
            val monthDays = if (isLeapMonth) {
                getLeapMonthDays(lunarYear)
            } else {
                getLunarMonthDays(lunarYear, lunarMonth)
            }
            
            if (remainingDays >= monthDays) {
                remainingDays -= monthDays
                if (isLeapMonth) {
                    isLeapMonth = false
                    lunarMonth++
                } else {
                    if (lunarMonth == leapMonth) {
                        isLeapMonth = true
                    } else {
                        lunarMonth++
                    }
                }
            } else {
                break
            }
        }
        
        lunarDay += remainingDays
        
        return LunarDate(
            year = lunarYear,
            month = if (isLeapMonth) -lunarMonth else lunarMonth,
            day = lunarDay,
            isLeapMonth = isLeapMonth,
            yearName = getYearName(lunarYear),
            monthName = getMonthName(lunarMonth, isLeapMonth),
            dayName = getDayName(lunarDay)
        )
    }
    
    /**
     * 获取农历年的总天数
     */
    private fun getLunarYearDays(year: Int): Int {
        var days = 348 // 12个月，每月29天
        val info = LUNAR_INFO[year - BASE_YEAR]
        
        // 计算12个月的大小月
        for (i in 0x8000 downTo 0x8) {
            days += if ((info and i.toLong()) != 0L) 1 else 0
        }
        
        // 加上闰月天数
        days += getLeapMonthDays(year)
        
        return days
    }
    
    /**
     * 获取农历月的天数
     */
    private fun getLunarMonthDays(year: Int, month: Int): Int {
        val info = LUNAR_INFO[year - BASE_YEAR]
        return if ((info and (0x10000 shr month).toLong()) != 0L) 30 else 29
    }
    
    /**
     * 获取闰月月份
     */
    private fun getLeapMonth(year: Int): Int {
        val info = LUNAR_INFO[year - BASE_YEAR]
        return (info and 0xf).toInt()
    }
    
    /**
     * 获取闰月天数
     */
    private fun getLeapMonthDays(year: Int): Int {
        val leapMonth = getLeapMonth(year)
        if (leapMonth == 0) return 0
        
        val info = LUNAR_INFO[year - BASE_YEAR]
        return if ((info and 0x10000) != 0L) 30 else 29
    }
    
    /**
     * 获取农历年份的天干地支名称
     */
    private fun getYearName(year: Int): String {
        val heavenlyIndex = (year - 4) % 10
        val earthlyIndex = (year - 4) % 12
        return "${HEAVENLY_STEMS[heavenlyIndex]}${EARTHLY_BRANCHES[earthlyIndex]}年"
    }
    
    /**
     * 获取农历月份名称
     */
    private fun getMonthName(month: Int, isLeapMonth: Boolean): String {
        val prefix = if (isLeapMonth) "闰" else ""
        return "$prefix${LUNAR_MONTHS[month - 1]}"
    }
    
    /**
     * 获取农历日期名称
     */
    private fun getDayName(day: Int): String {
        return if (day in 1..30) LUNAR_DAYS[day - 1] else "未知"
    }
}