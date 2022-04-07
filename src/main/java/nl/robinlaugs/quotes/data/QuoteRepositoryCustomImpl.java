package nl.robinlaugs.quotes.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import lombok.RequiredArgsConstructor;
import nl.robinlaugs.quotes.data.model.Quote;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class QuoteRepositoryCustomImpl implements QuoteRepositoryCustom {

    private final DynamoDBMapper dynamoDBMapper;

    /**
     * Perform a DynamoDB scan with a limit of 1 item based on a random start key
     * In case the random start key happened to be the final hash key, perform a new scan with a limit of 1 item without a start key
     *
     * @return An optional random Quote (empty when there are no quotes in the table)
     */
    @Override
    public Optional<Quote> getRandomQuote() {
        var randomScanExpression = new DynamoDBScanExpression()
                .withExclusiveStartKey(Map.of("id", new AttributeValue(UUID.randomUUID().toString())))
                .withLimit(1);

        return scanQuotes(randomScanExpression).or(() -> scanQuotes(new DynamoDBScanExpression().withLimit(1)));
    }

    private Optional<Quote> scanQuotes(DynamoDBScanExpression scanExpression) {
        return dynamoDBMapper.scan(Quote.class, scanExpression)
                .stream()
                .findFirst();
    }

}
