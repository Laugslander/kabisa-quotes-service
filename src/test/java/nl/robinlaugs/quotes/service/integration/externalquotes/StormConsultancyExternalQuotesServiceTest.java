package nl.robinlaugs.quotes.service.integration.externalquotes;

import nl.robinlaugs.quotes.TestObjectFactory;
import nl.robinlaugs.quotes.config.properties.StormConsultancyQuotesProperties;
import nl.robinlaugs.quotes.data.QuoteRepository;
import nl.robinlaugs.quotes.data.model.Quote;
import nl.robinlaugs.quotes.dto.StormConsultancyQuoteDto;
import nl.robinlaugs.quotes.dto.mapper.QuoteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StormConsultancyExternalQuotesServiceTest {

    @Mock
    private QuoteRepository quoteRepository;

    @Mock
    private QuoteMapper quoteMapper;

    @Mock
    private StormConsultancyQuotesProperties stormConsultancyQuotesProperties;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private StormConsultancyExternalQuotesService stormConsultancyExternalQuotesService;

    @BeforeEach
    void setUp() {
        when(stormConsultancyQuotesProperties.getUrl()).thenReturn("url");
        when(stormConsultancyQuotesProperties.getFullQuotesList()).thenReturn("fullQuotesList");
    }

    @Test
    void retrieveAndStoreQuotes() throws URISyntaxException {
        StormConsultancyQuoteDto stormConsultancyQuoteDto = TestObjectFactory.createStormConsultancyQuoteDto();
        Quote quote = TestObjectFactory.createQuote();

        when(restTemplate.getForObject(any(URI.class), eq(StormConsultancyQuoteDto[].class)))
                .thenReturn(new StormConsultancyQuoteDto[]{stormConsultancyQuoteDto});

        when(quoteMapper.map(stormConsultancyQuoteDto)).thenReturn(quote);

        stormConsultancyExternalQuotesService.retrieveAndStoreQuotes();

        verify(quoteRepository).saveAll(List.of(quote));
    }

    @Test
    void retrieveAndStoreQuotes_nullResponse() {
        when(restTemplate.getForObject(any(URI.class), eq(StormConsultancyQuoteDto[].class))).thenReturn(null);

        assertThrows(RuntimeException.class, () -> stormConsultancyExternalQuotesService.retrieveAndStoreQuotes());
    }

    @Test
    void retrieveAndStoreQuotes_emptyResponse() {
        when(restTemplate.getForObject(any(URI.class), eq(StormConsultancyQuoteDto[].class))).thenReturn(new StormConsultancyQuoteDto[0]);

        assertThrows(RuntimeException.class, () -> stormConsultancyExternalQuotesService.retrieveAndStoreQuotes());
    }

}