#!/usr/bin/make -f

run-app:
	docker-compose up

stop-app:
	docker-compose compose down