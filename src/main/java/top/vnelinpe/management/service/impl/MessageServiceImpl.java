package top.vnelinpe.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.vnelinpe.management.model.sys.SysMessageDO;
import top.vnelinpe.management.mapper.MessageMapper;
import top.vnelinpe.management.service.MessageService;

import java.util.Set;

/**
 * 模板管理
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/26 10:48
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    public SysMessageDO getBySceneCode(String sceneCode) {
        return messageMapper.findMessageBySceneCode(sceneCode);
    }

    @Override
    public Set<SysMessageDO> listMessages(){
        return messageMapper.listMessages();
    }
}
