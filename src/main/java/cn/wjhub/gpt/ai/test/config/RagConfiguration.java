package cn.wjhub.gpt.ai.test.config;

import cn.wjhub.gpt.ai.test.service.RagService;
import org.springframework.ai.client.AiClient;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RagConfiguration {

    @Bean
    public RagService ragService(@Qualifier("openAiClient") AiClient aiClient, @Qualifier("openAiEmbeddingClient") EmbeddingClient embeddingClient) {
        return new RagService(aiClient, embeddingClient);
    }

}