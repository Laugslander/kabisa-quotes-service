package nl.robinlaugs.quotes.service.integration;

import nl.robinlaugs.quotes.TestObjectFactory;
import nl.robinlaugs.quotes.config.properties.StormConsultancyQuotesProperties;
import nl.robinlaugs.quotes.data.QuoteRepository;
import nl.robinlaugs.quotes.data.model.Quote;
import nl.robinlaugs.quotes.dto.QuoteDto;
import nl.robinlaugs.quotes.dto.StormConsultancyQuoteDto;
import nl.robinlaugs.quotes.dto.mapper.StormConsultancyQuoteMapper;
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
class StormConsultancyQuotesServiceTest {

    @Mock
    private QuoteRepository quoteRepository;

    @Mock
    private StormConsultancyQuoteMapper stormConsultancyQuoteMapper;

    @Mock
    private StormConsultancyQuotesProperties stormConsultancyQuotesProperties;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private StormConsultancyQuotesService stormConsultancyQuotesService;

    @BeforeEach
    void setUp() {
        when(stormConsultancyQuotesProperties.getUrl()).thenReturn("url");
        when(stormConsultancyQuotesProperties.getFullQuotesList()).thenReturn("fullQuotesList");
    }

    @Test
    public void retrieveAndStoreQuotes() throws URISyntaxException {
        StormConsultancyQuoteDto stormConsultancyQuoteDto = TestObjectFactory.createStormConsultancyQuoteDto();
        Quote quote = TestObjectFactory.createQuote();

        when(restTemplate.getForObject(any(URI.class), eq(StormConsultancyQuoteDto[].class)))
                .thenReturn(new StormConsultancyQuoteDto[]{stormConsultancyQuoteDto});

        when(stormConsultancyQuoteMapper.mapDtoToEntity(stormConsultancyQuoteDto)).thenReturn(quote);

        stormConsultancyQuotesService.retrieveAndStoreQuotes();

        verify(quoteRepository).saveAll(List.of(quote));
    }

    @Test
    public void retrieveAndStoreQuotes_nullResponse() {
        when(restTemplate.getForObject(any(URI.class), eq(QuoteDto[].class))).thenReturn(null);

        assertThrows(RuntimeException.class, () -> stormConsultancyQuotesService.retrieveAndStoreQuotes());
    }

    @Test
    public void retrieveAndStoreQuotes_emptyResponse() {
        when(restTemplate.getForObject(any(URI.class), eq(QuoteDto[].class))).thenReturn(new QuoteDto[0]);

        assertThrows(RuntimeException.class, () -> stormConsultancyQuotesService.retrieveAndStoreQuotes());
    }

}