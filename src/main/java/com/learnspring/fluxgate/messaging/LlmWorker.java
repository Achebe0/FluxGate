package com.learnspring.fluxgate.messaging;

import com.learnspring.fluxgate.model.LlmJob;
import com.learnspring.fluxgate.routing.RoutingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class LlmWorker {

    private final RoutingService routingService;

    public LlmWorker(RoutingService routingService) {
        this.routingService = routingService;
    }

    @RabbitListener(queues = "llm.queue")
    public void handle(LlmJob job) {
        routingService.route(job);
    }
}
