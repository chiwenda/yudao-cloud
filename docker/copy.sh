#!/bin/sh

# 复制项目的文件到对应docker路径，便于一键生成镜像。
usage() {
	echo "Usage: sh copy.sh"
	exit 1
}




# copy jar
echo "begin copy fis-gateway "
cp ../project-gateway/target/project-gateway.jar ./project/gateway/jar
cp ../project-gateway/src/main/resources/application.yaml ./project/gateway/jar
cp ../project-gateway/src/main/resources/application-prod.yaml ./project/gateway/jar
cp ../project-gateway/src/main/resources/bootstrap.yaml ./project/gateway/jar
cp ../project-gateway/src/main/resources/bootstrap-prod.yaml ./project/gateway/jar
cp ../project-gateway/src/main/resources/logback-spring.xml ./project/gateway/jar

echo "begin copy fis-module-system "
cp ../project-module-system/project-module-system-biz/target/project-module-system.jar ./project/modules/system/jar
cp ../project-gateway/src/main/resources/application.yaml ./project/modules/system/jar
cp ../project-gateway/src/main/resources/application-prod.yaml ./project/modules/system/jar
cp ../project-gateway/src/main/resources/bootstrap.yaml ./project/modules/system/jar
cp ../project-gateway/src/main/resources/bootstrap-prod.yaml ./project/modules/system/jar
cp ../project-gateway/src/main/resources/logback-spring.xml ./project/modules/system/jar

echo "begin copy fis-module-infra "
cp ../project-module-infra/project-module-infra-biz/target/project-module-infra.jar ./project/modules/infra/jar
cp ../project-gateway/src/main/resources/application.yaml ./project/modules/infra/jar
cp ../project-gateway/src/main/resources/application-prod.yaml ./project/modules/infra/jar
cp ../project-gateway/src/main/resources/bootstrap.yaml ./project/modules/infra/jar
cp ../project-gateway/src/main/resources/bootstrap-prod.yaml ./project/modules/infra/jar
cp ../project-gateway/src/main/resources/logback-spring.xml ./project/modules/infra/jar

echo "begin copy fis-module-pc "
cp ../project-module-pc/project-module-pc-biz/target/project-module-pc.jar ./project/modules/pc/jar
cp ../project-gateway/src/main/resources/application.yaml ./project/modules/pc/jar
cp ../project-gateway/src/main/resources/application-prod.yaml ./project/modules/pc/jar
cp ../project-gateway/src/main/resources/bootstrap.yaml ./project/modules/pc/jar
cp ../project-gateway/src/main/resources/bootstrap-prod.yaml ./project/modules/pc/jar
cp ../project-gateway/src/main/resources/logback-spring.xml ./project/modules/pc/jar

