package top.vnelinpe.management.core.notify;

import java.util.concurrent.TimeoutException;

/**
 * 用户注册时的通知者
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/26 9:36
 */
public interface UserNotifier {
    /**
     * 发送提醒消息
     * @param sceneCode
     * @param args
     */
    void sendMessage(String sceneCode, String receiver, String... args);
}
