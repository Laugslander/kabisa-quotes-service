package nl.robinlaugs.quotes.service.integration.twitter;

import lombok.extern.slf4j.Slf4j;
import nl.robinlaugs.quotes.data.model.Quote;
import nl.robinlaugs.quotes.service.exception.TwitterIntegrationDisabledException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;

@Service
@ConditionalOnMissingBean(Twitter.class)
@Slf4j
public class TwitterServiceDisabled implements TwitterService {

    @Override
    public void sendTweet(Quote quote) {
        log.info("Unable to send Tweet because Twitter integration is disabled");

        throw new TwitterIntegrationDisabledException();
    }

}