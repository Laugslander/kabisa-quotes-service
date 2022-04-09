package nl.robinlaugs.quotes.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.robinlaugs.quotes.TestObjectFactory;
import nl.robinlaugs.quotes.data.model.Quote;
import nl.robinlaugs.quotes.dto.QuoteDto;
import nl.robinlaugs.quotes.dto.mapper.QuoteMapper;
import nl.robinlaugs.quotes.service.QuoteService;
import nl.robinlaugs.quotes.service.exception.QuoteNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuoteController.class)
class QuoteControllerTest {

    @MockBean
    private QuoteService quoteService;

    @MockBean
    private QuoteMapper quoteMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void get() throws Exception {
        Quote quote = TestObjectFactory.createQuote();
        QuoteDto quoteDto = TestObjectFactory.createQuoteDto();

        when(quoteService.getRandomQuote()).thenReturn(Optional.of(quote));

        when(quoteMapper.map(quote)).thenReturn(quoteDto);

        String json = new ObjectMapper().writeValueAsString(quoteDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(json));

        verify(quoteService).getRandomQuote();
    }

    @Test
    void getRandomQuote() throws Exception {
        Quote quote = TestObjectFactory.createQuote();
        QuoteDto quoteDto = TestObjectFactory.createQuoteDto();

        when(quoteService.getRandomQuote()).thenReturn(Optional.of(quote));

        when(quoteMapper.map(quote)).thenReturn(quoteDto);

        String json = new ObjectMapper().writeValueAsString(quoteDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/$random"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(json));

        verify(quoteService).getRandomQuote();
    }

    @Test
    void getById() throws Exception {
        String id = "id";

        Quote quote = TestObjectFactory.createQuote();
        quote.setId(id);

        QuoteDto quoteDto = TestObjectFactory.createQuoteDto();

        when(quoteService.getById(id)).thenReturn(Optional.of(quote));
        when(quoteMapper.map(quote)).thenReturn(quoteDto);

        String json = new ObjectMapper().writeValueAsString(quoteDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(json));

        verify(quoteService).getById(id);
        verify(quoteMapper).map(quote);
    }

    @Test
    void getById_notFound() throws Exception {
        String id = "unknown";

        when(quoteService.getById(id)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/" + id))
                .andExpect(status().isNotFound());

        verify(quoteService).getById(id);
    }

    @Test
    void shareQuote() throws Exception {
        String id = "id";

        mockMvc.perform(MockMvcRequestBuilders.post("/" + id + "/$share"))
                .andExpect(status().isOk());

        verify(quoteService).shareQuote(id);
    }

    @Test
    void shareQuote_notFound() throws Exception {
        String id = "unknown";

        doThrow(QuoteNotFoundException.class).when(quoteService).shareQuote(id);

        mockMvc.perform(MockMvcRequestBuilders.post("/" + id + "/$share"))
                .andExpect(status().isNotFound());

        verify(quoteService).shareQuote(id);
    }

    @Test
    void upvoteQuote() throws Exception {
        String id = "id";

        Quote quote = TestObjectFactory.createQuote();
        quote.setId(id);

        QuoteDto quoteDto = TestObjectFactory.createQuoteDto();

        when(quoteService.upvoteQuote(id)).thenReturn(quote);
        when(quoteMapper.map(quote)).thenReturn(quoteDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/" + id + "/$vote"))
                .andExpect(status().isOk());

        verify(quoteService).upvoteQuote(id);
        verify(quoteMapper).map(quote);
    }

    @Test
    void upvoteQuote_notFound() throws Exception {
        String id = "unknown";

        doThrow(QuoteNotFoundException.class).when(quoteService).upvoteQuote(id);

        mockMvc.perform(MockMvcRequestBuilders.post("/" + id + "/$vote"))
                .andExpect(status().isNotFound());

        verify(quoteService).upvoteQuote(id);
    }

    @Test
    void downvoteQuote() throws Exception {
        String id = "id";

        Quote quote = TestObjectFactory.createQuote();
        quote.setId(id);

        QuoteDto quoteDto = TestObjectFactory.createQuoteDto();

        when(quoteService.downvoteQuote(id)).thenReturn(quote);
        when(quoteMapper.map(quote)).thenReturn(quoteDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/" + id + "/$vote"))
                .andExpect(status().isOk());

        verify(quoteService).downvoteQuote(id);
        verify(quoteMapper).map(quote);
    }

    @Test
    void downveQuote_notFound() throws Exception {
        String id = "unknown";

        doThrow(QuoteNotFoundException.class).when(quoteService).downvoteQuote(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/" + id + "/$vote"))
                .andExpect(status().isNotFound());

        verify(quoteService).downvoteQuote(id);
    }


}