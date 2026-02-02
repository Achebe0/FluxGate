package com.learnspring.fluxgate;

import com.learnspring.fluxgate.repository.PromptLogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

//@SpringBootTest
class FluxGateApplicationTests {

    @MockBean
    private PromptLogRepository promptLogRepository;

    @Test
    void contextLoads() {
    }

}
