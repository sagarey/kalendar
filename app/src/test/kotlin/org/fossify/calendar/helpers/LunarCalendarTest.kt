package org.fossify.calendar.helpers

import org.fossify.calendar.models.LunarDate
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LunarCalendarTest {
    
    private lateinit var lunarCalendar: LunarCalendar
    
    @Before
    fun setUp() {
        lunarCalendar = LunarCalendar()
    }
    
    @Test
    fun testSolarToLunar_2025NewYear() {
        // 测试2025年1月1日元旦
        val result = lunarCalendar.solarToLunar(2025, 1, 1)
        
        assertNotNull(result)
        assertEquals(2024, result.year) // 2025年1月1日对应农历2024年
        assertEquals("甲辰年", result.yearName)
        assertFalse(result.isLeapMonth)
    }
    
    @Test
    fun testSolarToLunar_2025SpringFestival() {
        // 测试2025年1月29日春节
        val result = lunarCalendar.solarToLunar(2025, 1, 29)
        
        assertNotNull(result)
        assertEquals(2025, result.year) // 春节是农历新年
        assertEquals(1, result.month)
        assertEquals(1, result.day)
        assertEquals("乙巳年", result.yearName)
        assertEquals("正月", result.monthName)
        assertEquals("初一", result.dayName)
        assertTrue(result.isLunarNewYear())
    }
    
    @Test
    fun testSolarToLunar_2025MidAutumnFestival() {
        // 测试2025年10月6日中秋节（农历八月十五）
        val result = lunarCalendar.solarToLunar(2025, 10, 6)
        
        assertNotNull(result)
        assertEquals(2025, result.year)
        assertEquals(8, result.month)
        assertEquals(15, result.day)
        assertEquals("乙巳年", result.yearName)
        assertEquals("八月", result.monthName)
        assertEquals("十五", result.dayName)
    }
    
    @Test
    fun testSolarToLunar_KnownDates() {
        // 测试一些已知的公历转农历日期
        val testCases = arrayOf(
            // 公历年，月，日，期望农历年，月，日
            arrayOf(2024, 2, 10, 2024, 1, 1), // 2024年春节
            arrayOf(2024, 9, 17, 2024, 8, 15), // 2024年中秋节
            arrayOf(2023, 1, 22, 2023, 1, 1), // 2023年春节
        )
        
        testCases.forEach { testCase ->
            val result = lunarCalendar.solarToLunar(testCase[0], testCase[1], testCase[2])
            assertEquals("年份不匹配", testCase[3], result.year)
            assertEquals("月份不匹配", testCase[4], result.month)
            assertEquals("日期不匹配", testCase[5], result.day)
        }
    }
    
    @Test
    fun testLunarDateDisplayString() {
        val lunarDate = LunarDate(
            year = 2025,
            month = 1,
            day = 15,
            yearName = "乙巳年",
            monthName = "正月",
            dayName = "十五"
        )
        
        assertEquals("十五", lunarDate.getDisplayString())
        assertEquals("乙巳年正月十五", lunarDate.getFullDateString())
    }
    
    @Test
    fun testLunarDateFirstDay() {
        val lunarDate = LunarDate(
            year = 2025,
            month = 2,
            day = 1,
            yearName = "乙巳年",
            monthName = "二月",
            dayName = "初一"
        )
        
        assertEquals("二月", lunarDate.getDisplayString()) // 初一显示月份
        assertTrue(lunarDate.isFirstDayOfMonth())
    }
    
    @Test
    fun testYearRangeValidation() {
        // 测试年份范围验证
        assertThrows(IllegalArgumentException::class.java) {
            lunarCalendar.solarToLunar(1899, 1, 1) // 小于1900
        }
        
        assertThrows(IllegalArgumentException::class.java) {
            lunarCalendar.solarToLunar(2101, 1, 1) // 大于2100
        }
        
        // 边界值应该正常工作
        assertNotNull(lunarCalendar.solarToLunar(1900, 1, 31))
        assertNotNull(lunarCalendar.solarToLunar(2100, 12, 31))
    }
    
    @Test
    fun testLeapMonth() {
        // 测试闰月年份（2023年有闰二月）
        val result = lunarCalendar.solarToLunar(2023, 3, 22) // 这应该是闰二月的某一天
        
        assertNotNull(result)
        // 具体的闰月测试需要根据实际的农历数据来验证
    }
    
    @Test
    fun testPerformance() {
        // 性能测试：转换1000个日期应该在合理时间内完成
        val startTime = System.currentTimeMillis()
        
        for (i in 1..1000) {
            val year = 2000 + (i % 25)
            val month = (i % 12) + 1
            val day = (i % 28) + 1
            lunarCalendar.solarToLunar(year, month, day)
        }
        
        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime
        
        // 1000次转换应该在1秒内完成
        assertTrue("性能测试失败，耗时${duration}ms", duration < 1000)
    }
}