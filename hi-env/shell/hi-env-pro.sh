#!/usr/bin/env bash

set -e
#export PROJECT_NAME = hi-env
export BASE_DIR =/home/project/hi-w77996/
#export

#cd ${BASE_DIR}
#从git拉取最新代码
echo "pulling changes from git..."
#git pull origin master
echo "pulling changes from git...done"


#mvn编译开始
echo "building......"

#mvn clean package -P prod

echo "building...finish"

cur_time=`date +%Y%m%d%H%m`

echo ${cur_time}

#p = "$0"

echo "$1";

#sleep 10
#cp  ${BASE_DIR}/target/*.jar  /home/deploy/hi-w77996/
#
#echo "releasing....finish"
#
#echo "kill last pid"
#kill -9 `cat /home/deploy/hi-w77996/pidfile.txt`
#
#nohup java  -jar /home/deploy/hi-w77996/*.jar >nohup.out & echo $! > /home/admin/deploy/walle_biz/pidfile.txt
#
#tail -f nohup.out


