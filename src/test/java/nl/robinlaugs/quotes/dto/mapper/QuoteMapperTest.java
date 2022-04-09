package nl.robinlaugs.quotes.dto.mapper;

import nl.robinlaugs.quotes.TestObjectFactory;
import nl.robinlaugs.quotes.data.model.Quote;
import nl.robinlaugs.quotes.dto.QuoteDto;
import nl.robinlaugs.quotes.dto.StormConsultancyQuoteDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class QuoteMapperTest {

    private final QuoteMapper quoteMapper = new QuoteMapperImpl();

    @Test
    void mapQuoteToQuoteDto() {
        Quote quote = TestObjectFactory.createQuote();

        QuoteDto dto = quoteMapper.map(quote);

        assertEquals(quote.getId(), dto.id());
        assertEquals(quote.getAuthor(), dto.author());
        assertEquals(quote.getQuote(), dto.quote());
        assertEquals(quote.getVotes(), dto.votes());
    }

    @Test
    void mapStormConsultancyQuoteDtoToQuote() {
        StormConsultancyQuoteDto dto = TestObjectFactory.createStormConsultancyQuoteDto();

        Quote quote = quoteMapper.map(dto);

        assertEquals(dto.author(), quote.getAuthor());
        assertEquals(dto.quote(), quote.getQuote());
        assertNull(quote.getId());
        assertEquals(0, quote.getVotes());
    }

}