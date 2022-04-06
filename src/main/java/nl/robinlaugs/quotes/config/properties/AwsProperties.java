package nl.robinlaugs.quotes.config.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws")
@Getter
@Setter
public class AwsProperties {

    private String region;

    private DynamoDbProperties dynamoDb;

    @Getter
    @Setter
    public static class DynamoDbProperties {

        private String endpoint;

    }

}


