package nl.robinlaugs.quotes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.robinlaugs.quotes.service.integration.StormConsultancyQuotesService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ConfigurationPropertiesScan
@RequiredArgsConstructor
@Slf4j
public class Main implements CommandLineRunner {

    private final StormConsultancyQuotesService stormConsultancyQuotesService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        stormConsultancyQuotesService.retrieveAndStoreQuotes();
    }
}
