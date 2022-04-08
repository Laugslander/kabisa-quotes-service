package nl.robinlaugs.quotes.data.model;

import nl.robinlaugs.quotes.TestObjectFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuoteTest {

    @Test
    public void upvote() {
        Quote quote = TestObjectFactory.createQuote();
        int votes = quote.getVotes();

        quote.upvote();

        assertEquals(votes + 1, quote.getVotes());
    }

    @Test
    public void downvote() {
        Quote quote = TestObjectFactory.createQuote();
        int votes = quote.getVotes();

        quote.downvote();

        assertEquals(votes - 1, quote.getVotes());
    }

}