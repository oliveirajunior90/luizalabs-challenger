.PHONY: test-unit test-integration docker-up

docker-up:
	docker compose up -d

test-unit:
	./mvnw test

test-integration:
	./mvnw failsafe:integration-test failsafe:verify