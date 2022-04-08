package nl.robinlaugs.quotes.web;

import nl.robinlaugs.quotes.dto.ExceptionDto;
import nl.robinlaugs.quotes.service.exception.QuoteNotFoundException;
import nl.robinlaugs.quotes.service.exception.TwitterIntegrationDisabledException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class QuoteControllerAdvice {

    @ExceptionHandler(QuoteNotFoundException.class)
    ResponseEntity<?> quoteNotFoundHandler(QuoteNotFoundException e) {
        return ResponseEntity.notFound()
                .build();
    }

    @ExceptionHandler(TwitterIntegrationDisabledException.class)
    ResponseEntity<?> twitterIntegrationDisabledHandler(TwitterIntegrationDisabledException e) {
        return ResponseEntity.unprocessableEntity()
                .body(new ExceptionDto(e.getMessage()));
    }

}
