import org.joda.time.DateTime

// 模拟当前的算法来调试问题
fun debugLunarAlgorithm() {
    println("=== 农历算法调试 ===")
    
    // 测试几个不同的日期
    val testDates = listOf(
        DateTime(2025, 1, 15),  // 应该是腊月某日
        DateTime(2025, 2, 15),  // 应该是正月某日
        DateTime(2025, 3, 15),  // 应该是二月某日
        DateTime(2025, 6, 15),  // 应该是五月某日
        DateTime(2025, 8, 15),  // 应该是七月某日
    )
    
    for (date in testDates) {
        val baseDate = DateTime(1900, 1, 31, 0, 0, 0)
        val dayOffset = org.joda.time.Days.daysBetween(baseDate, date).days
        
        println("${date.year}-${date.monthOfYear}-${date.dayOfMonth}")
        println("  距离基准日期: ${dayOffset}天")
        
        // 简单计算年份
        val approxYear = 1900 + (dayOffset / 354) // 农历年平均354天
        val remainingDays = dayOffset % 354
        val approxMonth = (remainingDays / 29) + 1 // 农历月平均29天
        val approxDay = (remainingDays % 29) + 1
        
        println("  近似农历: ${approxYear}年${approxMonth}月${approxDay}日")
        println()
    }
}