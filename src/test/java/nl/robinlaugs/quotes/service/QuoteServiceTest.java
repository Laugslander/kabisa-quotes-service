package nl.robinlaugs.quotes.service;

import nl.robinlaugs.quotes.TestObjectFactory;
import nl.robinlaugs.quotes.data.QuoteRepository;
import nl.robinlaugs.quotes.data.model.Quote;
import nl.robinlaugs.quotes.service.exception.QuoteNotFoundException;
import nl.robinlaugs.quotes.service.integration.TwitterServiceEnabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuoteServiceTest {

    @Mock
    private TwitterServiceEnabled twitterService;

    @Mock
    private QuoteRepository quoteRepository;

    @InjectMocks
    private QuoteService quoteService;

    @Test
    public void getById() {
        String id = "id";

        Quote quote = TestObjectFactory.createQuote();
        quote.setId(id);

        when(quoteRepository.findById(quote.getId())).thenReturn(Optional.of(quote));

        Optional<Quote> result = quoteService.getById(id);

        verify(quoteRepository).findById(quote.getId());

        assertTrue(result.isPresent());
        assertEquals(quote, result.get());
    }

    @Test
    public void getRandomQuote() {
        Quote quote = TestObjectFactory.createQuote();

        when(quoteRepository.getRandomQuote()).thenReturn(Optional.of(quote));

        Optional<Quote> result = quoteService.getRandomQuote();

        verify(quoteRepository).getRandomQuote();

        assertTrue(result.isPresent());
        assertEquals(quote, result.get());
    }

    @Test
    public void shareQuote() {
        String id = "id";

        Quote quote = TestObjectFactory.createQuote();
        quote.setId(id);

        when(quoteRepository.findById(quote.getId())).thenReturn(Optional.of(quote));

        quoteService.shareQuote(id);

        verify(quoteRepository).findById(quote.getId());
        verify(twitterService).sendTweet(quote);
    }

    @Test
    public void shareQuote_unknownId() {
        String id = "unknown";

        when(quoteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(QuoteNotFoundException.class, () -> quoteService.shareQuote(id));
    }

}