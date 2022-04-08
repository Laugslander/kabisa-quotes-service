package nl.robinlaugs.quotes;

import nl.robinlaugs.quotes.data.model.Quote;
import nl.robinlaugs.quotes.dto.QuoteDto;
import nl.robinlaugs.quotes.dto.StormConsultancyQuoteDto;

public class TestObjectFactory {

    public static Quote createQuote() {
        return createQuote("quote");
    }

    public static Quote createQuote(String quote) {
        return Quote.builder()
                .author("author")
                .quote(quote)
                .votes(5)
                .build();
    }

    public static QuoteDto createQuoteDto() {
        return new QuoteDto("id", "author", "quote", 5);
    }

    public static StormConsultancyQuoteDto createStormConsultancyQuoteDto() {
        return new StormConsultancyQuoteDto("id", "author", "quote");
    }

}
