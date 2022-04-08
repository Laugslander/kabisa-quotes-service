package nl.robinlaugs.quotes.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.robinlaugs.quotes.TestObjectFactory;
import nl.robinlaugs.quotes.data.model.Quote;
import nl.robinlaugs.quotes.dto.QuoteDto;
import nl.robinlaugs.quotes.dto.mapper.QuoteMapper;
import nl.robinlaugs.quotes.service.QuoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

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
    public void get() throws Exception {
        Quote quote = TestObjectFactory.createQuote();
        QuoteDto quoteDto = TestObjectFactory.createQuoteDto();

        when(quoteService.getRandomQuote()).thenReturn(Optional.of(quote));

        when(quoteMapper.mapEntityToDto(quote)).thenReturn(quoteDto);

        String json = new ObjectMapper().writeValueAsString(quoteDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(json));

        verify(quoteService).getRandomQuote();
    }

    @Test
    public void getRandomQuote() throws Exception {
        Quote quote = TestObjectFactory.createQuote();
        QuoteDto quoteDto = TestObjectFactory.createQuoteDto();

        when(quoteService.getRandomQuote()).thenReturn(Optional.of(quote));

        when(quoteMapper.mapEntityToDto(quote)).thenReturn(quoteDto);

        String json = new ObjectMapper().writeValueAsString(quoteDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/$random"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(json));

        verify(quoteService).getRandomQuote();
    }

    @Test
    public void getById() throws Exception {
        String id = "id";
        Quote quote = TestObjectFactory.createQuote();
        QuoteDto quoteDto = TestObjectFactory.createQuoteDto();

        when(quoteService.getById(id)).thenReturn(Optional.of(quote));

        when(quoteMapper.mapEntityToDto(quote)).thenReturn(quoteDto);

        String json = new ObjectMapper().writeValueAsString(quoteDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(json));

        verify(quoteService).getById(id);
    }

    @Test
    public void shareQuote() throws Exception {
        String id = "id";

        mockMvc.perform(MockMvcRequestBuilders.post("/" + id + "/$share"))
                .andExpect(status().isOk());

        verify(quoteService).shareQuote(id);
    }

    @Test
    public void upvoteQuote() throws Exception {
        String id = "id";

        mockMvc.perform(MockMvcRequestBuilders.post("/" + id + "/$vote"))
                .andExpect(status().isOk());

        verify(quoteService).upvoteQuote(id);
    }

    @Test
    public void downvoteQuote() throws Exception {
        String id = "id";

        mockMvc.perform(MockMvcRequestBuilders.delete("/" + id + "/$vote"))
                .andExpect(status().isOk());

        verify(quoteService).downvoteQuote(id);
    }

}