package cn.wjhub.gpt.domain.service.impl;

import cn.wjhub.gpt.domain.service.AiChat;
import lombok.Getter;
import lombok.ToString;
import org.springframework.ai.client.AiClient;
import org.springframework.ai.prompt.Prompt;
import org.springframework.ai.prompt.SystemPromptTemplate;
import org.springframework.ai.prompt.messages.AssistantMessage;
import org.springframework.ai.prompt.messages.Message;
import org.springframework.ai.prompt.messages.UserMessage;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;


@Getter
@ToString
public class Completion implements AiChat, Serializable {
    private final AiClient aiClient;
    private Integer MAX_SIZE = 5;
    private static volatile List<Message> messages = new CopyOnWriteArrayList<>();
    private Message systemMessage = null;
    private Boolean useHistory = Boolean.TRUE;

    public Completion(AiClient aiClient, Integer MAX_SIZE, String systemPrompt) {
        this.aiClient = aiClient;
        this.MAX_SIZE = MAX_SIZE;
        this.systemMessage = new SystemPromptTemplate(systemPrompt).createMessage();
    }

    public Completion(AiClient aiClient, String systemPrompt, Boolean useHistory) {
        this.aiClient = aiClient;
        this.systemMessage = new SystemPromptTemplate(systemPrompt).createMessage();
        this.useHistory = useHistory;
    }

    public Completion(AiClient aiClient, Boolean useHistory) {
        this.aiClient = aiClient;
        this.useHistory = useHistory;
    }

    private void addUserMessage(String message) {
        Message userMessage = new UserMessage(message);
        if (!Boolean.TRUE.equals(this.useHistory)) {
            messages = new CopyOnWriteArrayList<>();
        }
        messages.add(userMessage);
    }

    private void addAssistantMessage(String message) {
        Message assistantMessage = new AssistantMessage(message);
        if (message.isEmpty() && Objects.nonNull(systemMessage)) {
            messages.add(0, systemMessage);
        }
        messages.add(assistantMessage);
    }

    @Override
    public String chat(String message) {
        addUserMessage(message);
        String result = aiClient.generate(new Prompt(messages)).getGeneration().getText();
        addAssistantMessage(result);
        update();
        return result;
    }

    private synchronized void update() {
        if (messages.size() > MAX_SIZE) {
            messages = messages.subList(messages.size() - MAX_SIZE, messages.size());
            if (Objects.isNull(systemMessage)) {
                return;
            }
            messages.add(0, systemMessage);
        }
    }
}