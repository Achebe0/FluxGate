package com.learnspring.fluxgate.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue llmQueue() {
        return new Queue("llm.queue", true);
    }

    @Bean
    public DirectExchange llmExchange() {
        return new DirectExchange("llm.exchange");
    }

    @Bean
    public Binding binding(Queue llmQueue, DirectExchange llmExchange) {
        return BindingBuilder
                .bind(llmQueue)
                .to(llmExchange)
                .with("llm.route");
    }
}
