#!/bin/bash

echo "🐳 Docker/容器化技术演示"
echo "================================"

echo -e "\n📋 当前环境信息:"
echo "主机名: $(hostname)"
echo "操作系统: $(uname -s)"
echo "内核版本: $(uname -r)"
echo "CPU架构: $(uname -m)"

echo -e "\n🔧 已安装的容器技术:"
if command -v docker &> /dev/null; then
    echo "✅ Docker: $(docker --version 2>/dev/null || echo '已安装但未运行')"
else
    echo "❌ Docker: 未安装"
fi

if command -v podman &> /dev/null; then
    echo "✅ Podman: $(podman --version)"
else
    echo "❌ Podman: 未安装"
fi

echo -e "\n📁 项目文件:"
ls -la /workspace/*.py /workspace/Dockerfile* 2>/dev/null || echo "文件未找到"

echo -e "\n🚀 运行 Hello World 应用:"
echo "================================"
python3 /workspace/hello.py

echo -e "\n📚 容器化技术说明:"
echo "================================"
echo "✨ Docker 是一个开源的容器化平台"
echo "✨ 容器提供了轻量级的虚拟化技术"
echo "✨ 容器可以打包应用程序及其依赖项"
echo "✨ 容器确保应用程序在任何环境中都能一致运行"
echo "✨ 相比虚拟机，容器更加高效和快速"

echo -e "\n🔍 常用 Docker 命令:"
echo "================================"
echo "docker run hello-world          # 运行 hello world 容器"
echo "docker build -t myapp .         # 构建镜像"
echo "docker ps                       # 查看运行中的容器"
echo "docker images                   # 查看本地镜像"
echo "docker pull ubuntu              # 拉取镜像"
echo "docker exec -it container bash  # 进入容器"

echo -e "\n✅ 演示完成！"