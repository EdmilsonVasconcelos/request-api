#!/usr/bin/make -f

build-artifact:
	mvn clean package -DskipTests

run-app:
	docker-compose up

stop-app:
	docker-compose compose down

run-db:
	docker-compose compose up mysqldb

stop-db:
	docker-compose stop mysqldb

run: build-artifact run-app