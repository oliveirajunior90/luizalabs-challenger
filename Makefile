.PHONY: test-unit test-integration docker-up

build-image:
	./mvnw spring-boot:build-image

docker-up: build-image
	docker compose up -d

start: docker-up

build:
	./mvnw clean package -DskipTests

test-unit:
	./mvnw test

test-integration:
	./mvnw failsafe:integration-test failsafe:verify

test-coverage:
	./mvnw clean verify