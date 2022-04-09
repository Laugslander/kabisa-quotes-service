package nl.robinlaugs.quotes.data;

import nl.robinlaugs.quotes.TestObjectFactory;
import nl.robinlaugs.quotes.data.model.Quote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class QuoteRepositoryCustomIntegrationTest extends BaseDynamoDbIntegrationTest {

    @Autowired
    private QuoteRepository quoteRepository;

    @Test
    void getRandomQuote() {
        List<Quote> quotes = IntStream.rangeClosed(1, 3)
                .mapToObj(i -> {
                    Quote quote = TestObjectFactory.createQuote();
                    quote.setQuote("quote " + i);
                    return quote;
                })
                .toList();

        quoteRepository.saveAll(quotes);

        Optional<Quote> result = quoteRepository.getRandomQuote();

        assertTrue(result.isPresent());
        assertTrue(quotes.contains(result.get()));
    }

}