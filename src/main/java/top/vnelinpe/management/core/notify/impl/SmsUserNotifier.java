package top.vnelinpe.management.core.notify.impl;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.vnelinpe.management.core.SmsHolder;
import top.vnelinpe.management.core.notify.UserNotifier;

/**
 * TODO
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/26 17:46
 */
@Component
public class SmsUserNotifier implements UserNotifier {

    @Autowired
    private SmsHolder smsHolder;

    @Override
    public void sendMessage(String sceneCode, String receiver, String... args) {
        // todo 校验手机号码
        try {
            smsHolder.send(sceneCode,new String[]{receiver},args);
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }
}
