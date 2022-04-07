package nl.robinlaugs.quotes.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class QuoteImportRunnerDefaultProfileTest {

    @SpyBean
    QuoteImportRunner quoteImportRunner;

    @Test
    void whenContextLoads_thenRunnersRun() throws Exception {
        verify(quoteImportRunner).run(any());
    }
}

@SpringBootTest
@ActiveProfiles("test")
class QuoteImportRunnerTestProfileTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void whenContextLoads_thenRunnersAreNotLoaded() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean(QuoteImportRunner.class));
    }

}