package com.servicewhale.chatbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
    private String content;

    @JsonProperty("threadId")
    private String threadId;

    private String assistantType;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

	public String getAssistantType() {
		return assistantType;
	}

	public void setAssistantType(String assistantType) {
		this.assistantType = assistantType;
	}
}