package cn.wjhub.gpt.langchain4j;


import java.time.Duration;

public class CustomerLocalAiModelBuilder {


    public static LocalAiChatModel buildLocalAiChatModel(String openAiApiKey, String baseUrl, String modelName, Double temperature, Double topP, Integer maxTokens) {
        return new LocalAiChatModel(openAiApiKey, baseUrl, modelName, temperature, topP, maxTokens, null, null, null, null);
    }

    public static LocalAiStreamingChatModel buildLocalAiStreamingChatModel(String openAiApiKey, String baseUrl,
                                                                           String modelName,
                                                                           Double temperature,
                                                                           Double topP,
                                                                           Integer maxTokens) {
        return new LocalAiStreamingChatModel(openAiApiKey, baseUrl, modelName, temperature, topP, maxTokens, null, null, null);
    }
}
