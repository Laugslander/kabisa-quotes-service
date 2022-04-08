package nl.robinlaugs.quotes.service.exception;

public class TwitterIntegrationDisabledException extends RuntimeException {

    public TwitterIntegrationDisabledException() {
        super("Twitter integration is disabled");
    }
}
