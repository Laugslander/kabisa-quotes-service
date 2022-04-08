package nl.robinlaugs.quotes.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "integration.twitter4j")
@Getter
@Setter
public class Twitter4JProperties {

    private Boolean enabled;
    private Boolean debug;

    private OAuth oAuth;

    @Getter
    @Setter
    public static class OAuth {

        private String consumerKey;
        private String consumerSecret;
        private String accessToken;
        private String accessTokenSecret;

    }

}
