#!/usr/bin/env bash
set -euo pipefail

sudo docker rm -f kafka zookeeper || true
sudo docker run -d --name zookeeper -p 2181:2181 -e ZOOKEEPER_CLIENT_PORT=2181 confluentinc/cp-zookeeper:latest
sudo docker run -d --name kafka --link zookeeper:zookeeper -p 9092:9092 \
  -e KAFKA_HEAP_OPTS="-Xms128M -Xmx256M" \
  -e KAFKA_BROKER_ID=1 \
  -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
  -e KAFKA_LISTENERS="PLAINTEXT://0.0.0.0:9092" \
  -e KAFKA_ADVERTISED_LISTENERS="PLAINTEXT://localhost:9092" \
  -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
  confluentinc/cp-kafka:5.5.0
sleep 10
sudo docker exec kafka kafka-topics --create --topic kafka-homework --zookeeper zookeeper:2181 --partitions 1 --replication-factor 1
