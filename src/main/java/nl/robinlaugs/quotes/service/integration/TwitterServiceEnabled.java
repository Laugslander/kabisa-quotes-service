package nl.robinlaugs.quotes.service.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.robinlaugs.quotes.data.model.Quote;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@Service
@ConditionalOnBean(Twitter.class)
@RequiredArgsConstructor
@Slf4j
public class TwitterServiceEnabled implements TwitterService {

    private final Twitter twitter;

    @Override
    public void sendTweet(Quote quote) {
        try {
            String text = String.format("'%s' - %s", quote.getQuote(), quote.getAuthor());
            Status status = twitter.updateStatus(text);

            log.info("Sent Tweet '{}' at {}", status.getText(), status.getCreatedAt());
        } catch (TwitterException e) {
            log.error("Unable to send Tweet", e);
        }
    }

}