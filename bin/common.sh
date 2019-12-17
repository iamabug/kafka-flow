#!/usr/bin/env bash

# 获取该脚本当前目录
if [ -L ${BASH_SOURCE-$0} ]; then
  FWDIR=$(dirname $(readlink "${BASH_SOURCE-$0}"))
else
  FWDIR=$(dirname "${BASH_SOURCE-$0}")
fi

if [[ -z "${KAFKAFLOW_HOME}" ]]; then
  export KAFKAFLOW_HOME="$(cd "${FWDIR}/.."; pwd)"
fi

if [[ -z "${KAFKAFLOW_CONF_DIR}" ]]; then
  export KAFKAFLOW_CONF_DIR="${KAFKAFLOW_HOME}/conf"
fi

if [[ -z "${KAFKAFLOW_LOG_DIR}" ]]; then
  export KAFKAFLOW_LOG_DIR="${KAFKAFLOW_HOME}/logs"
  export KAFKAFLOW_OUT_FILE="${KAFKAFLOW_LOG_DIR}/kafka-flow.out"
fi

if [[ -z "${KAFKAFLOW_PID_DIR}" ]]; then
  export KAFKAFLOW_PID_DIR="${KAFKAFLOW_HOME}/run"
  export KAFKAFLOW_PID_FILE="${KAFKAFLOW_PID_DIR}/kafka-flow.pid"
fi

if [[ -z "${KAFKAFLOW_WAR}" ]]; then
  export KAFKAFLOW_WAR=$(find "${KAFKAFLOW_HOME}/lib" -name "kafka-flow-web*.war")
fi

# Java
if [[ -n "${JAVA_HOME}" ]]; then
  export JAVA_RUNNER="${JAVA_HOME}/bin/java"
else
  export JAVA_RUNNER=java
fi

function addJarInDir(){
  if [[ -d "${1}" ]]; then
    KAFKAFLOW_CLASSPATH="${1}/*:${KAFKAFLOW_CLASSPATH}"
  fi
}

# build CLASSPATH
KAFKAFLOW_CLASSPATH=$CLASSPATH
addJarInDir "${KAFKAFLOW_HOME}/lib"

# MAIN_CLASS
export KAFKAFLOW_MAIN="iamabug.KafkaFlowMain"
export KAFKAFLOW_NAME="KafkaFlow"

# KafkaFlow 命令行参数
export KAFKAFLOW_ARGS="-Dwar.location=${KAFKAFLOW_WAR} -Dconf.dir=${KAFKAFLOW_CONF_DIR} -Dlogback.configurationFile=${KAFKAFLOW_CONF_DIR}/logback.xml -Dlog.dir=${KAFKAFLOW_LOG_DIR}"
