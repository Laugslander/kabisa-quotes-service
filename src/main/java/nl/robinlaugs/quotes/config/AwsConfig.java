package nl.robinlaugs.quotes.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import lombok.RequiredArgsConstructor;
import nl.robinlaugs.quotes.config.properties.AwsProperties;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableDynamoDBRepositories(basePackages = "nl.robinlaugs.quotes.data")
@RequiredArgsConstructor
public class AwsConfig {

    private final AwsProperties awsProperties;

    @Bean(name = "amazonDynamoDB")
    @Profile("!aws")
    public AmazonDynamoDB amazonDynamoDbLocal() {
        var endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(
                awsProperties.getDynamoDb().getEndpoint(),
                awsProperties.getRegion()
        );

        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .build();
    }

    @Bean(name = "amazonDynamoDB")
    @Profile("aws")
    public AmazonDynamoDB amazonDynamoDbAws() {
        return AmazonDynamoDBClientBuilder.defaultClient();
    }

}
