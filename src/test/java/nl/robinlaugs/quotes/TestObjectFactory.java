package nl.robinlaugs.quotes;

import nl.robinlaugs.quotes.data.model.Quote;
import nl.robinlaugs.quotes.dto.QuoteDto;

public class TestObjectFactory {

    public static Quote createQuote() {
        return createQuote("quote");
    }

    public static Quote createQuote(String quote) {
        return Quote.builder()
                .author("author")
                .quote(quote)
                .build();
    }

    public static QuoteDto createQuoteDto() {
        return new QuoteDto("author", "quote");
    }

}
