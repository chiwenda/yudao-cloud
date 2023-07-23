#!/bin/sh

# 使用说明，用来提示输入参数
usage() {
	echo "Usage: sh 执行脚本.sh [base|modules|stop|rm|clean]"
	exit 1
}

# 初始化
clean(){
  docker rmi $(docker images -q)
}

# 启动基础环境（必须）
base(){
  docker network create --driver overlay  prod
	docker-compose up -d project-nacos rmqnamesrv rmqbroker
}

# 启动程序模块（必须）
modules(){
	docker-compose up -d yudao-gateway yudao-module-system-biz yudao-module-infra-biz yudao-module-pc-biz
}

# 关闭所有环境/模块
stop(){
	docker-compose stop
}

# 删除所有环境/模块
rm(){
	docker-compose rm
}

# 根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
"clean")
	clean
;;
"base")
	base
;;
"modules")
	modules
;;
"stop")
	stop
;;
"rm")
	rm
;;
*)
	usage
;;
esac
