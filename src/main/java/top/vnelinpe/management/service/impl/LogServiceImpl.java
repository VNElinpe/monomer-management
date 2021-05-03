package top.vnelinpe.management.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.vnelinpe.management.dto.sys.PageOutDTO;
import top.vnelinpe.management.dto.sys.SysOptLogDTO;
import top.vnelinpe.management.query.sys.OptLogQuery;
import top.vnelinpe.management.query.sys.OptLogPageQuery;
import top.vnelinpe.management.vo.sys.OptLogVO;
import top.vnelinpe.management.vo.sys.PageOutVO;
import top.vnelinpe.management.model.sys.SysLogDO;
import top.vnelinpe.management.mapper.LogMapper;
import top.vnelinpe.management.service.LogService;

import java.util.List;

/**
 * 记录操作日志
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/20 8:45
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;

    @Async
    @Override
    public void addLog(SysLogDO sysLog) {
        logMapper.addLog(sysLog);
    }

    @Override
    public PageOutDTO<SysOptLogDTO> list(OptLogQuery optLogQuery) {
        if (optLogQuery instanceof OptLogPageQuery && ((OptLogPageQuery) optLogQuery).getPage() != null) {
            OptLogPageQuery logPageOptDTO = (OptLogPageQuery) optLogQuery;
            PageHelper.startPage(logPageOptDTO.getPage().getCurrentPage(), logPageOptDTO.getPage().getPageSize());
        }
        List<SysOptLogDTO> result = logMapper.list(optLogQuery);
        Page page = null;
        if (optLogQuery instanceof OptLogPageQuery && ((OptLogPageQuery) optLogQuery).getPage() != null) {
            page = (Page) result;
        }
        return PageOutDTO.<SysOptLogDTO>builder()
                .totalItems(page != null ? page.getTotal() : result.size())
                .totalPages(page != null ? page.getPages() : 1)
                .items(result)
                .build();
    }
}
