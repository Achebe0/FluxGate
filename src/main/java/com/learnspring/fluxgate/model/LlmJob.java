package com.learnspring.fluxgate.model;

import com.learnspring.fluxgate.ollama.TaskType;

public class LlmJob {

    private String jobId;
    private String prompt;
    private TaskType task;
    private boolean lowLatency;

    public LlmJob() {
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public TaskType getTask() {
        return task;
    }

    public void setTask(TaskType task) {
        this.task = task;
    }

    public boolean isLowLatency() {
        return lowLatency;
    }

    public void setLowLatency(boolean lowLatency) {
        this.lowLatency = lowLatency;
    }
}
