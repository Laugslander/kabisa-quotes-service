package nl.robinlaugs.quotes.service.integration.twitter;

import nl.robinlaugs.quotes.data.model.Quote;

public interface TwitterService {

    void sendTweet(Quote quote);

}
