package nl.robinlaugs.quotes.config;

import lombok.RequiredArgsConstructor;
import nl.robinlaugs.quotes.config.properties.Twitter4JProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
@RequiredArgsConstructor
public class Twitter4JConfig {

    private final Twitter4JProperties twitter4JProperties;

    @Bean
    @ConditionalOnProperty(prefix = "integration.twitter4j", name = "enabled", havingValue = "true")
    public Twitter twitter() {
        var oauthProperties = twitter4JProperties.getOAuth();

        var configuration = new ConfigurationBuilder()
                .setDebugEnabled(twitter4JProperties.getDebug())
                .setOAuthConsumerKey(oauthProperties.getConsumerKey())
                .setOAuthConsumerSecret(oauthProperties.getConsumerSecret())
                .setOAuthAccessToken(oauthProperties.getAccessToken())
                .setOAuthAccessTokenSecret(oauthProperties.getAccessTokenSecret())
                .build();

        return new TwitterFactory(configuration).getInstance();
    }

}
