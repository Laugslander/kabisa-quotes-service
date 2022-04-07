package nl.robinlaugs.quotes.data;

import nl.robinlaugs.quotes.data.model.Quote;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface QuoteRepository extends CrudRepository<Quote, String>, QuoteRepositoryCustom {
}
