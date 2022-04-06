# Kabisa Quotes Service

The Kabisa Quotes Service is part of the Kabisa cloud native coding assignment. The application uses Amazon DynamoDB for
data persistence. On startup, the application fetches programming quotes
from [Storm Consultancy's Programming Quotes API](http://quotes.stormconsultancy.co.uk/api).

### Prerequisites

- Java 17
- Docker

### Usage

1. Run DynamoDB locally with Docker:
   ```
   docker-compose up
   ```

2. Run the Spring Boot application:
   ```
   ./gradlew bootRun
   ```