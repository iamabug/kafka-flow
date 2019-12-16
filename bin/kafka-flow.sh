#!/usr/bin/env bash

if [ -L ${BASH_SOURCE-$0} ]; then
   BIN=$(dirname $(readlink "${BASH_SOURCE-$0}"))
 else
   BIN=$(dirname ${BASH_SOURCE-$0})
 fi
 BIN=$(cd "${BIN}">/dev/null; pwd)

 . "${BIN}/common.sh"


function initialize_default_directories() {
  if [[ ! -d "${KAFKAFLOW_LOG_DIR}" ]]; then
    echo "Log dir doesn't exist, create ${KAFKAFLOW_LOG_DIR}"
    mkdir -p "${KAFKAFLOW_LOG_DIR}"
  fi

  if [[ ! -d "${KAFKAFLOW_PID_DIR}" ]]; then
    echo "Pid dir doesn't exist, create ${KAFKAFLOW_PID_DIR}"
    mkdir -p "${KAFKAFLOW_PID_DIR}"
  fi
}


function start() {
  local pid
  initialize_default_directories
  nohup $JAVA_RUNNER $KAFKAFLOW_ARGS -cp $KAFKAFLOW_CLASSPATH $KAFKAFLOW_MAIN >> $KAFKAFLOW_OUT_FILE 2>&1 &
  pid=$!
  if [[ -z "${pid}" ]]; then
    echo -e "${KAFKAFLOW_NAME} failed to start"
    return 1;
  else
    echo -e "${KAFKAFLOW_NAME} started successfully !"
    echo ${pid} > "${KAFKAFLOW_PID_FILE}"
  fi
}

function stop() {
  local pid
  echo "Trying to stop ${KAFKAFLOW_NAME} ..."

  if [[ ! -f "${KAFKAFLOW_PID_FILE}" ]]; then
    echo "${KAFKAFLOW_NAME} is not running"
  else
    pid=$(cat "${KAFKAFLOW_PID_FILE}")
    if [[ -z "${pid}" ]]; then
      echo "${KAFKAFLOW_NAME} is not running"
    else
      for i in {1..10}; do
        check=$(ps -ef | grep "$pid" | grep -v 'grep')
        if [ -z "$check" ]; then
          echo "$KAFKAFLOW_NAME stopped successfully !"
          rm -f "${KAFKAFLOW_PID_FILE}"
          return
        fi
        kill -TERM "$pid"
        sleep 2
      done
      kill -9 "$pid"
      rm -f "${KAFKAFLOW_PID_FILE}"
      echo "$KAFKAFLOW_NAME stopped successfully !"
    fi
  fi
}



case "${1}" in
start)
  start
  ;;
stop)
  stop
  ;;
restart)
  stop
  start
  ;;
*)
  echo -e "Usage:\n\tkafka-flow.sh {start|stop|restart}\n"
esac
