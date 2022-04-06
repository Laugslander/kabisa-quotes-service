package nl.robinlaugs.quotes.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "integration.storm-consultancy-quotes")
@Getter
@Setter
public class StormConsultancyQuotesProperties {

    private String url;
    private String fullQuotesList;

}
