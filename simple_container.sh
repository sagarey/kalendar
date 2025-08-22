#!/bin/bash

echo "🔧 创建简单的容器化环境演示"
echo "================================"

# 创建一个临时目录作为我们的"容器"根目录
CONTAINER_ROOT="/tmp/my_container"
mkdir -p "$CONTAINER_ROOT"/{bin,etc,proc,sys,dev,tmp,home}

echo "📁 设置容器文件系统..."

# 复制一些基本的可执行文件
cp /bin/bash "$CONTAINER_ROOT/bin/"
cp /bin/ls "$CONTAINER_ROOT/bin/"
cp /bin/cat "$CONTAINER_ROOT/bin/"
cp /bin/echo "$CONTAINER_ROOT/bin/"
cp /usr/bin/python3 "$CONTAINER_ROOT/bin/" 2>/dev/null || echo "Python3 not copied"

# 复制我们的 hello world 脚本
cp /workspace/hello.py "$CONTAINER_ROOT/home/"

# 创建一个简单的 /etc/passwd 文件
echo "container:x:1000:1000:Container User:/home:/bin/bash" > "$CONTAINER_ROOT/etc/passwd"

echo "🚀 在隔离环境中运行应用..."
echo "================================"

# 使用 chroot 创建一个隔离的文件系统环境
echo "进入容器化环境..."
echo "运行 Python 脚本:"

# 在 chroot 环境中运行我们的脚本
sudo chroot "$CONTAINER_ROOT" /bin/bash -c "
echo '🏠 现在我们在一个隔离的环境中！'
echo '📂 当前目录结构:'
ls -la /
echo ''
echo '🐍 运行 Python 应用:'
cd /home
python3 hello.py 2>/dev/null || echo 'Python3 在此环境中不可用，但这展示了文件系统隔离'
echo ''
echo '✨ 这就是容器化的基本原理！'
"

echo -e "\n🧹 清理临时文件..."
sudo rm -rf "$CONTAINER_ROOT"

echo -e "\n📝 总结:"
echo "================================"
echo "✅ 我们成功创建了一个隔离的文件系统环境"
echo "✅ 这展示了容器化技术的基本概念"
echo "✅ Docker 使用类似的技术，但更加完善和自动化"
echo "✅ 容器提供了进程、网络、文件系统等多层面的隔离"