package nl.robinlaugs.quotes.service.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.robinlaugs.quotes.config.properties.StormConsultancyQuotesProperties;
import nl.robinlaugs.quotes.data.QuoteRepository;
import nl.robinlaugs.quotes.data.model.Quote;
import nl.robinlaugs.quotes.dto.QuoteDto;
import nl.robinlaugs.quotes.dto.mapper.QuoteMapper;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StormConsultancyQuotesService {

    private final QuoteRepository quoteRepository;
    private final QuoteMapper quoteMapper;
    private final StormConsultancyQuotesProperties stormConsultancyQuotesProperties;
    private final RestTemplate restTemplate;

    public void retrieveAndStoreQuotes() throws URISyntaxException {
        List<Quote> quotes = retrieveQuotes();

        quoteRepository.saveAll(quotes);

        log.info("Retrieved and stored {} quotes!", quoteRepository.count());
    }

    private List<Quote> retrieveQuotes() throws URISyntaxException {
        var uri = new URIBuilder(stormConsultancyQuotesProperties.getUrl())
                .setPath(stormConsultancyQuotesProperties.getFullQuotesList())
                .build()
                .normalize();

        var quotes = restTemplate.getForObject(uri, QuoteDto[].class);
        if (quotes == null || quotes.length == 0) {
            throw new RuntimeException("Unable to retrieve quotes from Storm Consultancy Quotes");
        }

        return Arrays.stream(quotes)
                .peek(quote -> log.debug("Retrieved quote: {}", quote))
                .map(quoteMapper::mapDtoToEntity)
                .toList();
    }

}
