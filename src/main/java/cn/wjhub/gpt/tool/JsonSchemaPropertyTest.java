package cn.wjhub.gpt.tool;

import java.lang.reflect.Method;
import java.util.*;

import static cn.wjhub.gpt.tool.ToolSpecifications.toolSpecificationFrom;

public class JsonSchemaPropertyTest {

    public static List<ToolSpecification> toolSpecifications;
    public static Map<String, ToolExecutor> toolExecutors;

    static class Weather {
        @Tool(name = "实时天气查询", value = {"提供指定地点的实时天气信息"})
        public String weather(String city) {
            System.out.println("Called weather with city=" + city);
            return city + "天气多云";
        }

    }

    public static void main(String[] args) {

        tools(List.of(new Weather()));
        String argumentsJSON = "{\"city\":\"Beijing\"}";

        ToolExecutionRequest toolExecutionRequest = ToolExecutionRequest.builder()
                .id("1")
                .name("实时天气查询")
                .arguments(argumentsJSON)
                .build();
        ToolExecutor toolExecutor = toolExecutors.get(toolExecutionRequest.name());
        String toolExecutionResult = toolExecutor.execute(toolExecutionRequest, "1");
        System.out.println(toolExecutionResult);
    }

    public static void tools(List<Object> objectsWithTools) {
        toolSpecifications = new ArrayList<>();
        toolExecutors = new HashMap<>();

        for (Object objectWithTool : objectsWithTools) {
            for (Method method : objectWithTool.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(Tool.class)) {
                    ToolSpecification toolSpecification = toolSpecificationFrom(method);
                    toolSpecifications.add(toolSpecification);
                    toolExecutors.put(toolSpecification.name(), new DefaultToolExecutor(objectWithTool, method));
                }
            }
        }
    }
}
