package cn.wjhub.gpt.langchain4j;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.agent.tool.ToolSpecifications;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.TokenStream;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ServiceWithToolsExample {
    static String openai_api_key = "sk-szsasasa";
    static String modelName = "gpt-3.5-turbo-1106";
    static String baseUrl = "sssss";

    static class Calculator {

        @Tool("Calculates the length of a string")
        int stringLength(String s) {
            System.out.println("Called stringLength with s='" + s + "'");
            return s.length();
        }

        @Tool("Calculates the sum of two numbers")
        int add(int a, int b) {
            System.out.println("Called add with a=" + a + ", b=" + b);
            return a + b;
        }

        @Tool("Calculates the square root of a number")
        double sqrt(int x) {
            System.out.println("Called sqrt with x=" + x);
            return Math.sqrt(x);
        }
    }

    // 天气插件工具
    static class Weather {

        @Tool(name = "实时天气查询", value = {"提供指定地点的实时天气信息"})
        String weather(String city) {
            System.out.println("Called weather with city=" + city);
            return city + "天气多云";
        }

    }

    interface Assistant {

        String chat(String userMessage);

        TokenStream chatStream(String message);
    }

    @Test
    public void should_return_answer() throws Exception {
        LocalAiChatModel build = CustomerLocalAiModelBuilder.buildLocalAiChatModel(openai_api_key, baseUrl, modelName, 0.7, 0.5, 1000);

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(build)
                .tools(new Calculator(), new Weather())
                .chatMemory(MessageWindowChatMemory.withMaxMessages(12))
                .build();
        String question = " 北京的天气怎么样?";

        String answer = assistant.chat(question);

        System.out.println(answer);
    }

    StreamingChatLanguageModel model = CustomerLocalAiModelBuilder.buildLocalAiStreamingChatModel(openai_api_key, baseUrl, modelName, 0.7, 0.5, 1000);

    @Test
    void should_stream_answer_and_return_response() throws Exception {
// when
        StringBuilder answerBuilder = new StringBuilder();
        CompletableFuture<Response<AiMessage>> futureResponse = new CompletableFuture<>();
        List<ToolSpecification> toolSpecifications = ToolSpecifications.toolSpecificationsFrom(Weather.class);
        toolSpecifications.addAll(ToolSpecifications.toolSpecificationsFrom(Calculator.class));
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new UserMessage("北京的天气怎么样?"));
        model.generate(messages, toolSpecifications, new StreamingResponseHandler<>() {

            @Override
            public void onNext(String token) {
                answerBuilder.append(token);
            }

            @Override
            public void onComplete(Response<AiMessage> response) {
                futureResponse.complete(response);
            }

            @Override
            public void onError(Throwable error) {
                futureResponse.completeExceptionally(error);
            }
        });

        Response<AiMessage> response = futureResponse.get(30, SECONDS);
        String streamedAnswer = answerBuilder.toString();
        System.out.println("streamedAnswer = " + streamedAnswer);


    }
}