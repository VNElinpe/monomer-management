package top.vnelinpe.management.service;

import top.vnelinpe.management.dto.sys.PageOutDTO;
import top.vnelinpe.management.dto.sys.SysOptLogDTO;
import top.vnelinpe.management.query.sys.OptLogQuery;
import top.vnelinpe.management.vo.sys.OptLogVO;
import top.vnelinpe.management.vo.sys.PageOutVO;
import top.vnelinpe.management.model.sys.SysLogDO;

/**
 * @author VNElinpe
 * @version 1.0
 * @date 2021/4/22 16:28
 */
public interface LogService {
    /**
     * 添加日志记录
     * @param sysLog
     */
    void addLog(SysLogDO sysLog);

    /**
     * 查询日志列表, 支持全部或分页
     * @param optLogQuery
     * @return
     */
    PageOutDTO<SysOptLogDTO> list(OptLogQuery optLogQuery);
}
