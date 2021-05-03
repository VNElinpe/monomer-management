package top.vnelinpe.management.service;

import top.vnelinpe.management.model.sys.SysMessageDO;

import java.util.Set;

/**
 * @author VNElinpe
 * @version 1.0
 * @date 2021/4/22 15:56
 */
public interface MessageService {
    /**
     * 通过场景码查询模板
     * @param sceneCode
     * @return
     */
    SysMessageDO getBySceneCode(String sceneCode);

    /**
     * 查询全部模板列表
     * @return
     */
    Set<SysMessageDO> listMessages();
}
