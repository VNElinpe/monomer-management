package top.vnelinpe.management.core.notify.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import top.vnelinpe.management.core.MessageHolder;
import top.vnelinpe.management.core.ShareVar;
import top.vnelinpe.management.core.notify.UserNotifier;
import top.vnelinpe.management.model.sys.SysMessageDO;

/**
 * 通过邮件通知用户
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/26 9:38
 */
@Service
public class MailUserNotifier implements UserNotifier {

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private JavaMailSender javaMailSender;


    @Autowired
    private MessageHolder messageHolder;

    @Override
    public void sendMessage(String sceneCode, String receiver, String... args) {
        SysMessageDO messageBySceneCode = messageHolder.getMessageBySceneCode(sceneCode);
        if(messageBySceneCode==null){
            return;
        }
        // todo 校验邮箱格式

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(receiver);
        simpleMailMessage.setSubject(messageBySceneCode.getUsage());
        String content = messageBySceneCode.getContent();
        for (String arg : args) {
            content = content.replaceFirst(ShareVar.PLACE_HOLDER_REG, arg);
        }
        simpleMailMessage.setText(content);
        javaMailSender.send(simpleMailMessage);
    }
}
