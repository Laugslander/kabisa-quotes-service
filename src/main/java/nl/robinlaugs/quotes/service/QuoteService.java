package nl.robinlaugs.quotes.service;

import lombok.RequiredArgsConstructor;
import nl.robinlaugs.quotes.data.QuoteRepository;
import nl.robinlaugs.quotes.data.model.Quote;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteRepository quoteRepository;

    public Optional<Quote> getRandomQuote() {
        return quoteRepository.getRandomQuote();
    }

}
