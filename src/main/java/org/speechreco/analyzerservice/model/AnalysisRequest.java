package org.speechreco.analyzerservice.model;

import lombok.Getter;

@Getter
public class AnalysisRequest {
    // Getters and setters
    private String text;
    private String prompt;

    public void setText(String text) {
        this.text = text;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}