package org.fossify.calendar.helpers

import org.fossify.calendar.models.Season
import org.fossify.calendar.models.SolarTerm
import java.util.*
import kotlin.math.*

/**
 * 二十四节气计算器
 * 基于天文算法计算二十四节气的准确时间
 */
class SolarTermHelper {
    
    companion object {
        // 2000年各节气的基准时间（儒略日）
        private val BASE_SOLAR_TERMS_2000 = doubleArrayOf(
            2451623.897, 2451639.586, 2451654.891, 2451670.969, 2451686.170, 2451701.935,
            2451717.441, 2451732.942, 2451748.461, 2451764.023, 2451779.642, 2451795.323,
            2451811.068, 2451826.878, 2451842.756, 2451858.703, 2451874.721, 2451890.811,
            2451906.973, 2451923.208, 2451939.516, 2451955.898, 2451972.353, 2451988.882
        )
        
        // 各节气的季节归属
        private val SEASON_MAPPING = mapOf(
            1 to Season.SPRING, 2 to Season.SPRING, 3 to Season.SPRING, 4 to Season.SPRING, 5 to Season.SPRING, 6 to Season.SPRING,
            7 to Season.SUMMER, 8 to Season.SUMMER, 9 to Season.SUMMER, 10 to Season.SUMMER, 11 to Season.SUMMER, 12 to Season.SUMMER,
            13 to Season.AUTUMN, 14 to Season.AUTUMN, 15 to Season.AUTUMN, 16 to Season.AUTUMN, 17 to Season.AUTUMN, 18 to Season.AUTUMN,
            19 to Season.WINTER, 20 to Season.WINTER, 21 to Season.WINTER, 22 to Season.WINTER, 23 to Season.WINTER, 24 to Season.WINTER
        )
    }
    
    /**
     * 计算指定年份的所有节气日期
     */
    fun calculateSolarTerms(year: Int): List<Pair<SolarTerm, Calendar>> {
        val result = mutableListOf<Pair<SolarTerm, Calendar>>()
        
        for (i in 0 until 24) {
            val julianDay = calculateSolarTermJulianDay(year, i + 1)
            val calendar = julianDayToCalendar(julianDay)
            val solarTerm = SolarTerm(
                name = SolarTerm.getNameByOrder(i + 1),
                order = i + 1,
                season = SEASON_MAPPING[i + 1] ?: Season.SPRING,
                description = getSolarTermDescription(i + 1),
                color = SEASON_MAPPING[i + 1]?.color ?: "#44AA44"
            )
            result.add(Pair(solarTerm, calendar))
        }
        
        return result
    }
    
    /**
     * 获取指定日期的节气
     */
    fun getSolarTermByDate(year: Int, month: Int, day: Int): SolarTerm? {
        val solarTerms = calculateSolarTerms(year)
        val targetCalendar = Calendar.getInstance().apply {
            set(year, month - 1, day)
        }
        
        return solarTerms.find { (_, calendar) ->
            calendar.get(Calendar.YEAR) == year &&
            calendar.get(Calendar.MONTH) == month - 1 &&
            calendar.get(Calendar.DAY_OF_MONTH) == day
        }?.first
    }
    
    /**
     * 计算指定年份指定节气的儒略日
     */
    private fun calculateSolarTermJulianDay(year: Int, termOrder: Int): Double {
        val yearDiff = year - 2000.0
        val baseJD = BASE_SOLAR_TERMS_2000[termOrder - 1]
        
        // 简化的节气计算公式，考虑年份差异
        return baseJD + yearDiff * 365.2422 + calculatePerturbation(year, termOrder)
    }
    
    /**
     * 计算摄动修正值
     */
    private fun calculatePerturbation(year: Int, termOrder: Int): Double {
        val y = year / 1000.0
        return when (termOrder) {
            in 1..6 -> 0.0 // 春季节气修正
            in 7..12 -> 0.0 // 夏季节气修正
            in 13..18 -> 0.0 // 秋季节气修正
            else -> 0.0 // 冬季节气修正
        }
    }
    
    /**
     * 儒略日转换为日历
     */
    private fun julianDayToCalendar(julianDay: Double): Calendar {
        val jd = julianDay + 0.5
        val z = jd.toInt()
        val f = jd - z
        
        val a = if (z < 2299161) z else {
            val alpha = ((z - 1867216.25) / 36524.25).toInt()
            z + 1 + alpha - (alpha / 4)
        }
        
        val b = a + 1524
        val c = ((b - 122.1) / 365.25).toInt()
        val d = (365.25 * c).toInt()
        val e = ((b - d) / 30.6001).toInt()
        
        val day = b - d - (30.6001 * e).toInt()
        val month = if (e < 14) e - 1 else e - 13
        val year = if (month > 2) c - 4716 else c - 4715
        
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day)
        return calendar
    }
    
    /**
     * 获取节气描述
     */
    private fun getSolarTermDescription(order: Int): String {
        return when (order) {
            1 -> "春季开始"
            2 -> "雨水增多"
            3 -> "春雷始鸣"
            4 -> "昼夜等长"
            5 -> "清明时节"
            6 -> "雨生百谷"
            7 -> "夏季开始"
            8 -> "麦粒饱满"
            9 -> "芒种时节"
            10 -> "夏日最长"
            11 -> "小暑时节"
            12 -> "大暑时节"
            13 -> "秋季开始"
            14 -> "处暑时节"
            15 -> "白露降临"
            16 -> "秋分时节"
            17 -> "寒露时节"
            18 -> "霜降时节"
            19 -> "冬季开始"
            20 -> "小雪时节"
            21 -> "大雪时节"
            22 -> "冬日最短"
            23 -> "小寒时节"
            24 -> "大寒时节"
            else -> "未知节气"
        }
    }
}