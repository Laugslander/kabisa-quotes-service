package nl.robinlaugs.quotes.service.exception;

public class QuoteNotFoundException extends RuntimeException {

    public QuoteNotFoundException(String id) {
        super(String.format("Quote with id %s was not found", id));
    }

}

