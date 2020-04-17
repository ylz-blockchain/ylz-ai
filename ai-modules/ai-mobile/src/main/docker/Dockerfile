# 运行环境
FROM jdk:8
# 创建 app 目录
RUN mkdir -p /home/project/mobile
WORKDIR /home/project/mobile
# 作者
MAINTAINER haifeng.lv
# 标识
LABEL app="mobile" version="0.0.1" by="haifeng.lv"
# 复制 jar包
COPY ./ai-mobile.jar /home/project/mobile
# 执行命令
CMD java -jar ai-mobile.jar