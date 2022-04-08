package nl.robinlaugs.quotes.service.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.robinlaugs.quotes.config.properties.StormConsultancyQuotesProperties;
import nl.robinlaugs.quotes.data.QuoteRepository;
import nl.robinlaugs.quotes.data.model.Quote;
import nl.robinlaugs.quotes.dto.StormConsultancyQuoteDto;
import nl.robinlaugs.quotes.dto.mapper.StormConsultancyQuoteMapper;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StormConsultancyQuotesService implements ExternalQuotesService {

    private final QuoteRepository quoteRepository;
    private final StormConsultancyQuoteMapper stormConsultancyQuoteMapper;
    private final StormConsultancyQuotesProperties stormConsultancyQuotesProperties;
    private final RestTemplate restTemplate;

    @Override
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

        StormConsultancyQuoteDto[] response = restTemplate.getForObject(uri, StormConsultancyQuoteDto[].class);

        List<StormConsultancyQuoteDto> quotes = Optional.ofNullable(response)
                .map(Arrays::asList)
                .orElseThrow(() -> new RuntimeException("Unable to retrieve quotes from Storm Consultancy Quotes"));

        return quotes.stream()
                .peek(quote -> log.debug("Retrieved quote: {}", quote))
                .map(stormConsultancyQuoteMapper::mapDtoToEntity)
                .toList();
    }

}
