package cn.wjhub.gpt.ai.test;

import org.springframework.ai.client.AiResponse;
import org.springframework.ai.prompt.Prompt;

public interface AiClient {

    default String generate(String message) { // implementation omitted

        return message;
    }

    AiResponse generate(Prompt prompt);

}