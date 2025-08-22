package org.fossify.calendar.helpers

import org.fossify.calendar.models.ChineseFestival
import org.fossify.calendar.models.FestivalType

/**
 * 中国节日数据管理器
 * 提供中国传统节日、现代节日和国际节日的数据管理功能
 */
class ChineseFestivalHelper {
    
    companion object {
        // 传统节日（农历）
        private val TRADITIONAL_FESTIVALS = listOf(
            ChineseFestival("春节", FestivalType.TRADITIONAL, true, 1, 1, 1, "#FF4444", "农历新年"),
            ChineseFestival("元宵节", FestivalType.TRADITIONAL, true, 1, 15, 2, "#FF4444", "正月十五"),
            ChineseFestival("龙抬头", FestivalType.TRADITIONAL, true, 2, 2, 3, "#FF4444", "二月二"),
            ChineseFestival("端午节", FestivalType.TRADITIONAL, true, 5, 5, 1, "#FF4444", "五月初五"),
            ChineseFestival("七夕节", FestivalType.TRADITIONAL, true, 7, 7, 2, "#FF4444", "七月初七"),
            ChineseFestival("中元节", FestivalType.TRADITIONAL, true, 7, 15, 3, "#FF4444", "七月十五"),
            ChineseFestival("中秋节", FestivalType.TRADITIONAL, true, 8, 15, 1, "#FF4444", "八月十五"),
            ChineseFestival("重阳节", FestivalType.TRADITIONAL, true, 9, 9, 2, "#FF4444", "九月初九"),
            ChineseFestival("腊八节", FestivalType.TRADITIONAL, true, 12, 8, 3, "#FF4444", "腊月初八"),
            ChineseFestival("小年", FestivalType.TRADITIONAL, true, 12, 23, 2, "#FF4444", "腊月二十三"),
            ChineseFestival("除夕", FestivalType.TRADITIONAL, true, 12, 30, 1, "#FF4444", "腊月三十")
        )
        
        // 现代节日（公历）
        private val MODERN_FESTIVALS = listOf(
            ChineseFestival("元旦", FestivalType.MODERN, false, 1, 1, 1, "#4444FF", "公历新年"),
            ChineseFestival("植树节", FestivalType.MODERN, false, 3, 12, 3, "#4444FF", "植树造林"),
            ChineseFestival("消费者权益日", FestivalType.MODERN, false, 3, 15, 4, "#4444FF", "消费者保护"),
            ChineseFestival("清明节", FestivalType.MODERN, false, 4, 5, 1, "#4444FF", "祭祖扫墓"),
            ChineseFestival("劳动节", FestivalType.MODERN, false, 5, 1, 1, "#4444FF", "国际劳动节"),
            ChineseFestival("青年节", FestivalType.MODERN, false, 5, 4, 2, "#4444FF", "五四青年节"),
            ChineseFestival("护士节", FestivalType.MODERN, false, 5, 12, 3, "#4444FF", "国际护士节"),
            ChineseFestival("儿童节", FestivalType.MODERN, false, 6, 1, 2, "#4444FF", "六一儿童节"),
            ChineseFestival("建党节", FestivalType.MODERN, false, 7, 1, 2, "#4444FF", "中国共产党成立"),
            ChineseFestival("建军节", FestivalType.MODERN, false, 8, 1, 2, "#4444FF", "中国人民解放军建军"),
            ChineseFestival("教师节", FestivalType.MODERN, false, 9, 10, 2, "#4444FF", "尊师重教"),
            ChineseFestival("国庆节", FestivalType.MODERN, false, 10, 1, 1, "#4444FF", "中华人民共和国成立")
        )
        
        // 国际节日（公历）
        private val INTERNATIONAL_FESTIVALS = listOf(
            ChineseFestival("情人节", FestivalType.INTERNATIONAL, false, 2, 14, 3, "#44AA44", "西方情人节"),
            ChineseFestival("妇女节", FestivalType.INTERNATIONAL, false, 3, 8, 2, "#44AA44", "国际妇女节"),
            ChineseFestival("愚人节", FestivalType.INTERNATIONAL, false, 4, 1, 4, "#44AA44", "愚人节"),
            ChineseFestival("地球日", FestivalType.INTERNATIONAL, false, 4, 22, 3, "#44AA44", "世界地球日"),
            ChineseFestival("母亲节", FestivalType.INTERNATIONAL, false, 5, 8, 2, "#44AA44", "母亲节"),
            ChineseFestival("环境日", FestivalType.INTERNATIONAL, false, 6, 5, 3, "#44AA44", "世界环境日"),
            ChineseFestival("父亲节", FestivalType.INTERNATIONAL, false, 6, 19, 3, "#44AA44", "父亲节"),
            ChineseFestival("万圣节", FestivalType.INTERNATIONAL, false, 10, 31, 4, "#44AA44", "万圣节"),
            ChineseFestival("圣诞节", FestivalType.INTERNATIONAL, false, 12, 25, 3, "#44AA44", "圣诞节")
        )
    }
    
    /**
     * 获取所有节日
     */
    fun getAllFestivals(): List<ChineseFestival> {
        return TRADITIONAL_FESTIVALS + MODERN_FESTIVALS + INTERNATIONAL_FESTIVALS
    }
    
    /**
     * 获取传统节日
     */
    fun getTraditionalFestivals(): List<ChineseFestival> {
        return TRADITIONAL_FESTIVALS
    }
    
    /**
     * 获取现代节日
     */
    fun getModernFestivals(): List<ChineseFestival> {
        return MODERN_FESTIVALS
    }
    
    /**
     * 获取国际节日
     */
    fun getInternationalFestivals(): List<ChineseFestival> {
        return INTERNATIONAL_FESTIVALS
    }
    
    /**
     * 根据日期获取节日（公历）
     * @param month 月份 (1-12)
     * @param day 日期
     * @return 该日期的节日列表
     */
    fun getFestivalsByDate(month: Int, day: Int): List<ChineseFestival> {
        return getAllFestivals().filter { 
            !it.isLunar && it.month == month && it.day == day 
        }.sortedBy { it.priority }
    }
    
    /**
     * 根据农历日期获取节日
     * @param month 农历月份 (1-12)
     * @param day 农历日期
     * @return 该农历日期的节日列表
     */
    fun getFestivalsByLunarDate(month: Int, day: Int): List<ChineseFestival> {
        return getAllFestivals().filter { 
            it.isLunar && it.month == month && it.day == day 
        }.sortedBy { it.priority }
    }
    
    /**
     * 根据节日类型获取节日
     */
    fun getFestivalsByType(type: FestivalType): List<ChineseFestival> {
        return getAllFestivals().filter { it.type == type }
    }
    
    /**
     * 根据名称搜索节日
     */
    fun searchFestivalsByName(name: String): List<ChineseFestival> {
        return getAllFestivals().filter { it.name.contains(name) }
    }
    
    /**
     * 获取指定优先级的节日
     */
    fun getFestivalsByPriority(priority: Int): List<ChineseFestival> {
        return getAllFestivals().filter { it.priority <= priority }
    }
    
    /**
     * 判断指定日期是否为节日
     */
    fun isFestival(month: Int, day: Int, isLunar: Boolean = false): Boolean {
        return if (isLunar) {
            getFestivalsByLunarDate(month, day).isNotEmpty()
        } else {
            getFestivalsByDate(month, day).isNotEmpty()
        }
    }
    
    /**
     * 获取节日的主要显示名称（优先级最高的节日）
     */
    fun getPrimaryFestivalName(month: Int, day: Int, isLunar: Boolean = false): String? {
        val festivals = if (isLunar) {
            getFestivalsByLunarDate(month, day)
        } else {
            getFestivalsByDate(month, day)
        }
        return festivals.firstOrNull()?.name
    }
}