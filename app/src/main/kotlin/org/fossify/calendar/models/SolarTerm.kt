package org.fossify.calendar.models

/**
 * 二十四节气数据模型
 * @param name 节气名称
 * @param order 节气序号 (1-24)
 * @param season 所属季节
 * @param description 节气描述
 * @param color 节气标识颜色
 */
data class SolarTerm(
    val name: String,
    val order: Int,
    val season: Season,
    val description: String = "",
    val color: String = "#44AA44"
) {
    /**
     * 获取节气的显示名称
     */
    fun getDisplayName(): String = name
    
    /**
     * 判断是否为季节开始的节气
     */
    fun isSeasonStart(): Boolean {
        return order in listOf(1, 7, 13, 19) // 立春、立夏、立秋、立冬
    }
    
    companion object {
        /**
         * 二十四节气名称数组
         */
        val SOLAR_TERM_NAMES = arrayOf(
            "立春", "雨水", "惊蛰", "春分", "清明", "谷雨",      // 春季
            "立夏", "小满", "芒种", "夏至", "小暑", "大暑",      // 夏季
            "立秋", "处暑", "白露", "秋分", "寒露", "霜降",      // 秋季
            "立冬", "小雪", "大雪", "冬至", "小寒", "大寒"       // 冬季
        )
        
        /**
         * 根据序号获取节气名称
         */
        fun getNameByOrder(order: Int): String {
            return if (order in 1..24) SOLAR_TERM_NAMES[order - 1] else "未知"
        }
        
        /**
         * 根据名称获取节气序号
         */
        fun getOrderByName(name: String): Int {
            return SOLAR_TERM_NAMES.indexOf(name) + 1
        }
    }
}

/**
 * 季节枚举
 */
enum class Season(val chineseName: String, val color: String) {
    SPRING("春", "#44AA44"),    // 绿色
    SUMMER("夏", "#FF6644"),    // 橙红色
    AUTUMN("秋", "#FFAA44"),    // 橙黄色
    WINTER("冬", "#4488FF")     // 蓝色
}