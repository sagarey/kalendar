#!/usr/bin/env python3

print("🐳 Hello from Container!")
print("这是一个运行在容器中的 Python 应用程序")
print("Container technology is working! 🚀")

import os
import platform

print(f"\n系统信息:")
print(f"操作系统: {platform.system()}")
print(f"Python 版本: {platform.python_version()}")
print(f"主机名: {os.uname().nodename}")
print(f"当前用户: {os.environ.get('USER', 'unknown')}")