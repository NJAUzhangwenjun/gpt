package cn.wjhub.gpt.ai.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AiController {

    private final Completion completion;

    public AiController(Completion completion) {
        this.completion = completion;
    }

    @GetMapping("/chat")
    public String chat(
            @RequestParam(value = "message", defaultValue = "Hi") String message) {
        return completion.chat(message);
    }
}