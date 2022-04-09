package nl.robinlaugs.quotes.service.integration.twitter;

import nl.robinlaugs.quotes.TestObjectFactory;
import nl.robinlaugs.quotes.data.model.Quote;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.sql.Date;
import java.time.Instant;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TwitterServiceEnabledTest {

    @Mock
    private Twitter twitter;

    @InjectMocks
    private TwitterServiceEnabled twitterService;

    @Test
    void sendTweet() throws TwitterException {
        Quote quote = TestObjectFactory.createQuote();
        String text = String.format("'%s' - %s", quote.getQuote(), quote.getAuthor());

        Status status = Mockito.mock(Status.class);
        when(status.getText()).thenReturn(text);
        when(status.getCreatedAt()).thenReturn(Date.from(Instant.now()));

        when(twitter.updateStatus(text)).thenReturn(status);

        twitterService.sendTweet(quote);

        verify(twitter).updateStatus(text);
    }

}