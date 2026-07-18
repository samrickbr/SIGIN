package br.com.inova.sigin.shared;

import br.com.inova.sigin.dev.service.DevService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class BaseIntegrationTest {
    @Autowired
    protected TestFactory testFactory;

    @Autowired
    protected DevService devService;

    @BeforeEach
    void setup() {
        devService.popular();
    }
}