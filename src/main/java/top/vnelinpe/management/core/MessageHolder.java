package top.vnelinpe.management.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.vnelinpe.management.model.sys.SysMessageDO;
import top.vnelinpe.management.service.impl.MessageServiceImpl;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author VNElinpe
 * @version 1.0
 * @date 2021/4/22 15:53
 */
@Component
public class MessageHolder {
    @Autowired
    private MessageServiceImpl messageService;
    private Map<String, SysMessageDO> messages;

    @PostConstruct
    private void init(){
        Set<SysMessageDO> sysMessages = messageService.listMessages();
        messages=new HashMap<>();
        sysMessages.forEach(msg->messages.put(msg.getSceneCode(),msg));
    }

    public SysMessageDO getMessageBySceneCode(String sceneCode){
        return messages.get(sceneCode);
    }
}
