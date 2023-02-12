#!/usr/bin/make -f

build-artifact:
	mvn clean package -DskipTests

run-app:
	docker-compose up

stop-app:
	docker-compose compose down

run-db:
	docker-compose compose up db -d

stop-db:
	docker-compose stop db

run: build-artifact run-app