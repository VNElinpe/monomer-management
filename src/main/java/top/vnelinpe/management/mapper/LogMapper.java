package top.vnelinpe.management.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.vnelinpe.management.dto.sys.SysOptLogDTO;
import top.vnelinpe.management.query.sys.OptLogQuery;
import top.vnelinpe.management.vo.sys.OptLogVO;
import top.vnelinpe.management.model.sys.SysLogDO;

import java.util.List;

/**
 * 记录日志的mapper
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/20 8:46
 */
@Mapper
public interface LogMapper {
    /**
     * 添加日志
     * @param sysLog
     */
    void addLog(SysLogDO sysLog);

    /**
     * 查询日志信息
     * @param optLogQuery
     * @return
     */
    List<SysOptLogDTO> list(OptLogQuery optLogQuery);
}
