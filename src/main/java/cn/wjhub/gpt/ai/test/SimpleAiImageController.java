package cn.wjhub.gpt.ai.test;

import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.ImageResult;
import com.theokanning.openai.moderation.ModerationRequest;
import com.theokanning.openai.moderation.ModerationResult;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.client.AiClient;
import org.springframework.ai.prompt.Prompt;
import org.springframework.ai.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/ai/image")
public class SimpleAiImageController {

    private final OpenAiService openAiService;

    @Value("classpath:/static/prompts/image-prompt.st")
    private Resource imagePromptResource;

    private final AiClient aiClient;


    @Autowired
    public SimpleAiImageController(AiClient aiClient) throws NoSuchFieldException, IllegalAccessException {
        this.aiClient = aiClient;
        Field api = aiClient.getClass().getDeclaredField("openAiService");
        api.setAccessible(true);
        openAiService = (OpenAiService) api.get(aiClient);
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(value = "message", defaultValue = "给我讲一个笑话") String message) {
        return aiClient.generate(message);
    }

    @GetMapping("/image")
    public String image(@RequestParam(value = "message", defaultValue = "帮我画一个美女图片") String message) {
        CreateImageRequest imageRequest = new CreateImageRequest();
        PromptTemplate promptTemplate = new PromptTemplate(imagePromptResource);
        Prompt prompt = promptTemplate.create(Map.of("message", message));
        imageRequest.setPrompt(prompt.getContents());
        ImageResult image = openAiService.createImage(imageRequest);

        return image.getData().get(0).getUrl();
    }

    @GetMapping("/moderation")
    public ModerationResult moderation(@RequestParam(value = "message", defaultValue = "帮我画一个美女图片") String message) {
        ModerationRequest request = new ModerationRequest();
        request.setModel("text-moderation-latest");
        request.setInput(message);
        return openAiService.createModeration(request);
    }
}
