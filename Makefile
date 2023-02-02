#!/usr/bin/make -f

run-back:
	docker-compose up -d app

stop-back:
	docker-compose stop app