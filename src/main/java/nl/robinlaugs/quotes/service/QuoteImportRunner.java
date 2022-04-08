package nl.robinlaugs.quotes.service;

import lombok.RequiredArgsConstructor;
import nl.robinlaugs.quotes.service.integration.ExternalQuotesService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
@RequiredArgsConstructor
public class QuoteImportRunner implements CommandLineRunner {

    private final ExternalQuotesService externalQuotesService;

    @Override
    public void run(String... args) throws Exception {
        externalQuotesService.retrieveAndStoreQuotes();
    }

}
