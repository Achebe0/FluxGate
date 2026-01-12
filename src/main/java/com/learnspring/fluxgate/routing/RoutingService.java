package com.learnspring.fluxgate.routing;

import com.learnspring.fluxgate.model.LlmJob;
import com.learnspring.fluxgate.ollama.ModelType;
import com.learnspring.fluxgate.ollama.OllamaClient;
import com.learnspring.fluxgate.ollama.TaskType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoutingService {

    private final Map<ModelType, OllamaClient> clients;

    public RoutingService(List<OllamaClient> clientList) {
        this.clients = clientList.stream()
                .collect(Collectors.toMap(
                        OllamaClient::getModelType,
                        c -> c
                ));
    }

    public void route(LlmJob job) {
        ModelType model = selectModel(job);
        clients.get(model).generate(job.getPrompt());
    }

    private ModelType selectModel(LlmJob job) {
        if (job.getTask() == TaskType.CODE) {
            return ModelType.DEEPSEEK_CODER;
        }
        if (job.isLowLatency()) {
            return ModelType.LLAMA3;
        }
        return ModelType.MISTRAL;
    }
}
