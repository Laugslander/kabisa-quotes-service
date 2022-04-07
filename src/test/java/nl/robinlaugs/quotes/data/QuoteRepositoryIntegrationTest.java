package nl.robinlaugs.quotes.data;


import nl.robinlaugs.quotes.TestObjectFactory;
import nl.robinlaugs.quotes.data.model.Quote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


class QuoteRepositoryIntegrationTest extends BaseDynamoDbIntegrationTest {

    @Autowired
    private QuoteRepository quoteRepository;

    @Test
    void crudTestScenario() {
        Quote quote = TestObjectFactory.createQuote();

        testSave(quote);
        testFindById(quote);
        testUpdate(quote);
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

    private void testUpdate(Quote quote) {
        String author = "updated author";
        quote.setAuthor(author);

        Quote result = quoteRepository.save(quote);

        assertEquals(author, result.getAuthor());
    }

    private void testDelete(Quote quote) {
        quoteRepository.delete(quote);

        Optional<Quote> result = quoteRepository.findById(quote.getId());

        assertTrue(result.isEmpty());
    }

}