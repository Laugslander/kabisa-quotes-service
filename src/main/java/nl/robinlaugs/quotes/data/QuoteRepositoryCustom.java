package nl.robinlaugs.quotes.data;

import nl.robinlaugs.quotes.data.model.Quote;

import java.util.Optional;

public interface QuoteRepositoryCustom {

    Optional<Quote> getRandomQuote();

}
