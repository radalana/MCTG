build:
	mvn clean package
run:
	mvn clean package exec:java	
	
test-login:
	./testlogin.sh pause
test:
	/bin/bash -i ./test12.sh pause
full-test:
	./MonsterTradingCards.sh pause
create-packs:
	./test_create_packages.sh pause
up:
	docker-compose up -d
down:
	docker-compose down
reinit-db:
	docker-compose down -v
	docker-compose build
	docker-compose up -d

