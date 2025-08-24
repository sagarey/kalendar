#!/bin/bash

# GitHub Actions 监控脚本
# 监控 Android APK Build & Test workflow 的运行状态

echo "🚀 开始监控 GitHub Actions workflow..."
echo "=========================================="

# 获取最新的 workflow run
get_latest_run() {
    gh run list --workflow="android-emulator-testing.yml" --limit=1 --json status,conclusion,url,createdAt,displayTitle
}

# 格式化时间
format_time() {
    date -d "$1" "+%Y-%m-%d %H:%M:%S"
}

# 监控循环
while true; do
    # 清屏
    clear
    
    echo "🤖 Android APK Build & Test - 实时监控"
    echo "=========================================="
    echo "⏰ $(date '+%Y-%m-%d %H:%M:%S')"
    echo ""
    
    # 获取运行信息
    run_info=$(get_latest_run)
    
    if [ -n "$run_info" ]; then
        status=$(echo "$run_info" | jq -r '.[0].status')
        conclusion=$(echo "$run_info" | jq -r '.[0].conclusion')
        url=$(echo "$run_info" | jq -r '.[0].url')
        created_at=$(echo "$run_info" | jq -r '.[0].createdAt')
        title=$(echo "$run_info" | jq -r '.[0].displayTitle')
        
        # 格式化创建时间
        formatted_time=$(format_time "$created_at")
        
        echo "📋 最新运行信息:"
        echo "   标题: $title"
        echo "   创建时间: $formatted_time"
        echo "   状态: $status"
        
        # 根据状态显示不同的图标和颜色提示
        case "$status" in
            "queued")
                echo "   🟡 状态: 排队中..."
                ;;
            "in_progress")
                echo "   🔵 状态: 运行中..."
                ;;
            "completed")
                if [ "$conclusion" = "success" ]; then
                    echo "   ✅ 状态: 成功完成!"
                    echo ""
                    echo "🎉 测试完成! 可以查看结果了:"
                    echo "   📊 查看详情: $url"
                    echo ""
                    echo "📱 接下来可以查看:"
                    echo "   • GitHub Actions Summary 中的 AI 分析报告"
                    echo "   • GitHub Pages 上的截图和分析文件"
                    echo ""
                    echo "监控结束。按 Ctrl+C 退出。"
                    break
                elif [ "$conclusion" = "failure" ]; then
                    echo "   ❌ 状态: 运行失败"
                    echo "   🔗 查看详情: $url"
                    break
                else
                    echo "   ⚠️  状态: $conclusion"
                    echo "   🔗 查看详情: $url"
                    break
                fi
                ;;
            "cancelled")
                echo "   🟠 状态: 已取消"
                break
                ;;
            *)
                echo "   ❓ 状态: $status"
                ;;
        esac
        
        echo "   🔗 查看详情: $url"
        
        # 如果还在运行中，显示进度提示
        if [ "$status" = "in_progress" ] || [ "$status" = "queued" ]; then
            echo ""
            echo "⏳ 预计运行时间: 10-15 分钟"
            echo "📋 当前步骤可能包括:"
            echo "   • 设置环境 (Java, Android SDK, Gradle)"
            echo "   • 安装 Cursor CLI"
            echo "   • 构建 APK"
            echo "   • 启动 Android 模拟器"
            echo "   • 运行测试和截图"
            echo "   • AI 分析报告生成"
            echo "   • 上传到 GitHub Pages"
        fi
    else
        echo "❌ 无法获取 workflow 运行信息"
        echo "请检查:"
        echo "   • GitHub CLI 是否已登录 (gh auth status)"
        echo "   • 仓库权限是否正确"
    fi
    
    echo ""
    echo "🔄 每 30 秒自动刷新... (按 Ctrl+C 退出)"
    echo "=========================================="
    
    # 等待30秒后刷新
    sleep 30
done
