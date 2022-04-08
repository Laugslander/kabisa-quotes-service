package nl.robinlaugs.quotes.web.controller;

import lombok.RequiredArgsConstructor;
import nl.robinlaugs.quotes.data.model.Quote;
import nl.robinlaugs.quotes.dto.QuoteDto;
import nl.robinlaugs.quotes.dto.mapper.QuoteMapper;
import nl.robinlaugs.quotes.service.QuoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;
    private final QuoteMapper quoteMapper;

    @GetMapping
    ResponseEntity<QuoteDto> get() {
        return getRandomQuote();
    }

    @GetMapping("/$random")
    ResponseEntity<QuoteDto> getRandomQuote() {
        return quoteService.getRandomQuote()
                .map(quoteMapper::mapEntityToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    ResponseEntity<QuoteDto> getById(@PathVariable String id) {
        return quoteService.getById(id)
                .map(quoteMapper::mapEntityToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/$share")
    ResponseEntity<?> shareQuote(@PathVariable String id) {
        quoteService.shareQuote(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/$vote")
    ResponseEntity<?> upvoteQuote(@PathVariable String id) {
        Quote quote = quoteService.upvoteQuote(id);
        QuoteDto dto = quoteMapper.mapEntityToDto(quote);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}/$vote")
    ResponseEntity<?> downvoteQuote(@PathVariable String id) {
        Quote quote = quoteService.downvoteQuote(id);
        QuoteDto dto = quoteMapper.mapEntityToDto(quote);

        return ResponseEntity.ok(dto);
    }

}
