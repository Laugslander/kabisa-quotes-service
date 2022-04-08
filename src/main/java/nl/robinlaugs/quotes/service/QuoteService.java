package nl.robinlaugs.quotes.service;

import lombok.RequiredArgsConstructor;
import nl.robinlaugs.quotes.data.QuoteRepository;
import nl.robinlaugs.quotes.data.model.Quote;
import nl.robinlaugs.quotes.service.exception.QuoteNotFoundException;
import nl.robinlaugs.quotes.service.integration.TwitterService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final TwitterService twitterService;
    private final QuoteRepository quoteRepository;

    public Optional<Quote> getById(String id) {
        return quoteRepository.findById(id);
    }

    public Optional<Quote> getRandomQuote() {
        return quoteRepository.getRandomQuote();
    }

    public void shareQuote(String id) {
        Quote quote = quoteRepository.findById(id).orElseThrow(() -> new QuoteNotFoundException(id));

        twitterService.sendTweet(quote);
    }

}
