package cn.wjhub.gpt.ai.test.controller;

import cn.wjhub.gpt.domain.service.AiChat;
import cn.wjhub.gpt.domain.service.impl.Completion;
import org.springframework.ai.client.AiClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AiController {

    private final AiChat aiChat;

    public AiController(AiClient aiClient) {
        this.aiChat = new Completion(aiClient, Boolean.TRUE);
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(value = "message", defaultValue = "Hi") String message) {
        return aiChat.chat(message);
    }
}