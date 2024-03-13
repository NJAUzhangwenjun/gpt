package cn.wjhub.gpt.tool;


public interface ToolExecutor {

    String execute(ToolExecutionRequest toolExecutionRequest, Object memoryId);
}
