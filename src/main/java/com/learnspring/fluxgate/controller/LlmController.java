package com.learnspring.fluxgate.controller;

import com.learnspring.fluxgate.model.LlmJob;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/llm")
public class LlmController {

    private final RabbitTemplate rabbitTemplate;

    public LlmController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/submit")
    public String submit(@RequestBody LlmJob job) {
        rabbitTemplate.convertAndSend(
                "llm.exchange",
                "llm.route",
                job
        );
        return job.getJobId();
    }
}
