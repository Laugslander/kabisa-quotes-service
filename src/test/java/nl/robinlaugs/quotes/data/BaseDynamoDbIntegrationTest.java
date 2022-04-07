package nl.robinlaugs.quotes.data;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = {BaseDynamoDbIntegrationTest.DynamoDBInitializer.class})
@Testcontainers
abstract class BaseDynamoDbIntegrationTest {

    private static final String DYNAMODB_DOCKER_IMAGE = "amazon/dynamodb-local:latest";
    private static final int DYNAMODB_PORT = 8000;

    public static GenericContainer<?> dynamoDbContainer = new GenericContainer<>(DYNAMODB_DOCKER_IMAGE)
            .withExposedPorts(DYNAMODB_PORT);

    static {
        dynamoDbContainer.start();
    }

    static class DynamoDBInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext context) {
            var containerEndpoint = String.format("http://%s:%s",
                    dynamoDbContainer.getHost(),
                    dynamoDbContainer.getFirstMappedPort()
            );

            var properties = Map.of("aws.dynamodb.endpoint", containerEndpoint);

            TestPropertyValues.of(properties).applyTo(context.getEnvironment());
        }
    }

}
