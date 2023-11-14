package cn.wjhub.gpt.ai.test;

import org.springframework.ai.client.AiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleAiController {

    private final AiClient aiClient;

    @Autowired
    public SimpleAiController(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    @GetMapping("/ai/simple")
    public String completion(@RequestParam(value = "message", defaultValue = "给我讲一个笑话") String message) {
        return aiClient.generate(message);
    }
}