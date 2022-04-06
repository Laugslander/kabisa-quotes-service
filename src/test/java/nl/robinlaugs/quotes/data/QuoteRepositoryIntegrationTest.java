package nl.robinlaugs.quotes.data;


import nl.robinlaugs.quotes.data.model.Quote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
class QuoteRepositoryIntegrationTest implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String DYNAMODB_DOCKER_IMAGE = "amazon/dynamodb-local:latest";
    private static final int DYNAMODB_PORT = 8000;

    @Container
    public static GenericContainer dynamoDbContainer = new GenericContainer(DYNAMODB_DOCKER_IMAGE)
            .withExposedPorts(DYNAMODB_PORT);

    public void initialize(ConfigurableApplicationContext context) {
        var endpoint = String.format("%s:%s", dynamoDbContainer.getHost(), dynamoDbContainer.getFirstMappedPort());
        var properties = Map.of("aws.dynamodb.endpoint", endpoint);

        TestPropertyValues.of(properties).applyTo(context.getEnvironment());
    }

    @Autowired
    private QuoteRepository quoteRepository;

    @Test
    public void integrationTestScenario() {
        Quote quote = Quote.builder()
                .author("author")
                .quote("quote")
                .build();

        testSave(quote);
        testFindById(quote);
        testDelete(quote);
    }

    private void testSave(Quote quote) {
        Quote result = quoteRepository.save(quote);

        assertNotNull(result);
    }

    private void testFindById(Quote quote) {
        Optional<Quote> result = quoteRepository.findById(quote.getId());

        assertTrue(result.isPresent());
        assertEquals(quote, result.get());
    }

    private void testDelete(Quote quote) {
        quoteRepository.delete(quote);

        Optional<Quote> result = quoteRepository.findById(quote.getId());

        assertTrue(result.isEmpty());
    }

}