#!/usr/bin/env bash


case "${1}" in
start)
  start
  ;;
stop)
  stop
  ;;
restart)
  restart
  ;;
*)
  echo -e "Usage:\n\tkafka-flow.sh {start|stop|restart}\n"
esac

# symlink situation is not handled
BASEPATH="$( cd "$(dirname "$0")" ; pwd -P )"
SUNNY_MAIN=iamabug.KafkaFlowMain

