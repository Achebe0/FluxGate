package com.learnspring.fluxgate.messaging;

import com.learnspring.fluxgate.config.RabbitConfig;
import com.learnspring.fluxgate.dto.PromptLogDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class LogProducer {

    private final RabbitTemplate rabbitTemplate;

    public LogProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendLog(PromptLogDTO logDTO) {
        rabbitTemplate.convertAndSend(
            RabbitConfig.EXCHANGE_NAME, 
            RabbitConfig.ROUTING_KEY, 
            logDTO
        );
        System.out.println(" [x] Sent log to RabbitMQ: " + logDTO.selectedModel());
    }
}