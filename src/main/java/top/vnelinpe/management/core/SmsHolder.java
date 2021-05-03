package top.vnelinpe.management.core;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 处理sms的类
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/26 17:49
 */
@Component
public class SmsHolder {
    @Value("${sms.security-id}")
    private String securityId;
    @Value("${sms.security-key}")
    private String securityKey;
    @Value("${sms.app-id}")
    private String appId;
    @Value("${sms.sign}")
    private String sign;

    public void send(String templateId, String[] phones, String[] args) throws TencentCloudSDKException {
        Credential cred = new Credential(securityId, securityKey);
        ClientProfile clientProfile = new ClientProfile();
        SmsClient client = new SmsClient(cred, "", clientProfile);
        SendSmsRequest req = new SendSmsRequest();
        req.setSmsSdkAppid(appId);
        req.setSign(sign);
        req.setTemplateID(templateId);
        req.setPhoneNumberSet(phones);
        req.setTemplateParamSet(args);
        SendSmsResponse res = client.SendSms(req);
    }
}
