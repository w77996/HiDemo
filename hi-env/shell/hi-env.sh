#!/usr/bin/env bash
#1.简单的部署脚本
#!/bin/bash

#获取项目进程ID
PROJECT_NAME="yuntu_barrage-0.0.1-SNAPSHOT.jar"
service_name="弹幕服务"
service_pid=`ps -ef | grep $PROJECT_NAME | grep -v grep | awk '{print $2}'`

echo "进程ID为：$service_pid"

#杀进程
echo "kill service..."
for id in $service_pid
do
    kill -9 $id
done

echo "$service_pid 已杀死..."

echo "$service_name 重启中..."

nohup java  -Xmx128m -Xms64m -Xmn64m -Xss128m -jar $PROJECT_NAME >nohup.out &

tail -f nohup.out

#chmod u+x *.sh