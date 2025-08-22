# 使用官方 Python 基础镜像
FROM python:3.11-slim

# 设置工作目录
WORKDIR /app

# 复制 Python 脚本到容器中
COPY hello.py .

# 使脚本可执行
RUN chmod +x hello.py

# 运行应用程序
CMD ["python3", "hello.py"]