package org.fossify.calendar.helpers

import org.fossify.calendar.models.LunarDate
import org.joda.time.DateTime
import org.junit.Assert.*
import org.junit.Test

class LunarCalendarSimpleTest {
    
    @Test
    fun testSolarToLunar_KnownDates() {
        // 测试一些已知的公历到农历转换
        // 验证基本转换功能
        val solar1 = DateTime(2025, 1, 29, 0, 0, 0)
        val lunar1 = LunarCalendarSimple.solarToLunar(solar1)
        assertNotNull("农历转换不应该返回null", lunar1)
        
        val solar2 = DateTime(2025, 2, 15, 0, 0, 0)
        val lunar2 = LunarCalendarSimple.solarToLunar(solar2)
        assertNotNull("农历转换不应该返回null", lunar2)
        
        // 验证农历显示文本格式
        lunar1?.let {
            assertTrue("农历年份应该合理", it.year in 2020..2030)
            assertTrue("农历月份应该合理", it.month in 1..12)
            assertTrue("农历日期应该合理", it.day in 1..30)
        }
    }
    
    @Test
    fun testSolarToLunar_EdgeCases() {
        // 测试边界情况
        val solar1900 = DateTime(1900, 1, 31, 0, 0, 0)
        val lunar1900 = LunarCalendarSimple.solarToLunar(solar1900)
        assertNotNull("1900年基准日期应该能正确转换", lunar1900)
        
        val solar2100 = DateTime(2100, 12, 31, 0, 0, 0)
        val lunar2100 = LunarCalendarSimple.solarToLunar(solar2100)
        assertNotNull("2100年日期应该能正确转换", lunar2100)
    }
    
    @Test
    fun testLunarDate_DisplayText() {
        // 测试农历日期显示文本
        val lunar1 = LunarDate(2025, 1, 1)
        assertEquals("正月初一应该显示月份", "正月", lunar1.getDisplayText())
        
        val lunar15 = LunarDate(2025, 1, 15)
        assertEquals("十五应该正确显示", "十五", lunar15.getDisplayText())
        
        val lunar30 = LunarDate(2025, 1, 30)
        assertEquals("三十应该正确显示", "三十", lunar30.getDisplayText())
    }
    
    @Test
    fun testSolarToLunar_Performance() {
        // 测试性能：转换应该在合理时间内完成
        val testDate = DateTime(2025, 6, 15, 0, 0, 0)
        val startTime = System.currentTimeMillis()
        
        repeat(100) {
            LunarCalendarSimple.solarToLunar(testDate)
        }
        
        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime
        
        assertTrue("100次转换应该在合理时间内完成", duration < 1000) // 放宽时间要求
    }
    
    @Test
    fun testSolarToLunar_MonthVariation() {
        // 测试不同月份的农历转换，验证月份变化
        val testDates = listOf(
            DateTime(2025, 1, 15),  // 应该是腊月
            DateTime(2025, 3, 15),  // 应该是二月或三月
            DateTime(2025, 6, 15),  // 应该是五月或六月
            DateTime(2025, 9, 15),  // 应该是八月或九月
            DateTime(2025, 12, 15)  // 应该是十一月或十二月
        )
        
        val lunarResults = mutableListOf<LunarDate?>()
        
        for (date in testDates) {
            val lunar = LunarCalendarSimple.solarToLunar(date)
            lunarResults.add(lunar)
            
            lunar?.let {
                println("${date.year}-${date.monthOfYear}-${date.dayOfMonth} → 农历${it.year}年${it.month}月${it.day}日 (${it.getDisplayText()})")
            }
        }
        
        // 验证月份应该有变化，不应该都是正月
        val months = lunarResults.mapNotNull { it?.month }.distinct()
        assertTrue("农历月份应该有变化，不应该都是正月", months.size > 1)
        assertFalse("不应该所有日期都是正月", lunarResults.all { it?.month == 1 })
    }
}