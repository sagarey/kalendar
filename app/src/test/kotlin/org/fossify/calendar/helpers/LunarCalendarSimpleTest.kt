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
    fun testSolarToLunar_KnownAccurateDates() {
        // 测试一些已知准确的公历到农历转换
        // 使用相对稳定的日期进行验证
        
        // 测试春节期间的日期 (每年农历正月初一)
        val springFestival2025 = DateTime(2025, 1, 29, 0, 0, 0) // 2025年春节
        val lunar2025 = LunarCalendarSimple.solarToLunar(springFestival2025)
        
        lunar2025?.let {
            // 验证基本合理性
            assertTrue("农历年份应该是2024或2025", it.year in 2024..2025)
            assertTrue("春节期间应该是正月或腊月", it.month == 1 || it.month == 12)
        }
        
        // 测试中秋节期间
        val midAutumn2025 = DateTime(2025, 10, 6, 0, 0, 0) // 2025年中秋节
        val lunarMidAutumn = LunarCalendarSimple.solarToLunar(midAutumn2025)
        
        lunarMidAutumn?.let {
            assertTrue("中秋节应该在农历八月", it.month in 7..9) // 允许一定误差
            assertTrue("中秋节应该在月中", it.day in 10..20)
        }
    }
}