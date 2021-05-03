package top.vnelinpe.management.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.vnelinpe.management.model.sys.SysMessageDO;

import java.util.Set;

/**
 * TODO
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/26 10:53
 */
@Mapper
public interface MessageMapper {
    /**
     * 按场景码查询模板信息
     * @param sceneCode
     * @return
     */
    SysMessageDO findMessageBySceneCode(String sceneCode);

    /**
     * 查询所有的模板
     * @return
     */
    Set<SysMessageDO> listMessages();
}
