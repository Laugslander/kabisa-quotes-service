package nl.robinlaugs.quotes.service.integration;

import nl.robinlaugs.quotes.TestObjectFactory;
import nl.robinlaugs.quotes.data.model.Quote;
import nl.robinlaugs.quotes.service.exception.TwitterIntegrationDisabledException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TwitterServiceDisabledTest {

    @InjectMocks
    private TwitterServiceDisabled twitterService;

    @Test
    public void sendTweet() {
        Quote quote = TestObjectFactory.createQuote();

        assertThrows(TwitterIntegrationDisabledException.class, () -> twitterService.sendTweet(quote));
    }

}