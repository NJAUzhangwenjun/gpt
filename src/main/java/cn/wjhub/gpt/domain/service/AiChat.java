package cn.wjhub.gpt.domain.service;

public interface AiChat {
    /**
     * 聊天
     *
     * @param message 消息
     * @return 响应
     */
    public String chat(String message);

}