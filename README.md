# Kabisa Quotes Service

The Kabisa Quotes Service is part of the Kabisa cloud native coding assignment. The application uses Amazon DynamoDB for
data persistence. On startup, the application fetches programming quotes
from [Storm Consultancy's Programming Quotes API](http://quotes.stormconsultancy.co.uk/api).

### Prerequisites

- Java 17
- Docker

### Optional setup

Optionally, enable Twitter integration by configuring Twitter4J properties in `application.yml` with keys and tokens,
obtained from the [Twitter Developer Portal](https://developer.twitter.com). When not configured, the application runs
without Twitter integration.

### Start

1. Run DynamoDB locally with Docker:
   ```
   docker-compose up
   ```

2. Run the Spring Boot application:
   ```
   ./gradlew bootRun
   ```

### Functionality

- [x] Retrieve random quotes
- [x] Fetch quotes from external service
- [x] Share quotes on Twitter

Refer to the Postman collection in the `postman` folder for all available REST endpoints. 