package top.vnelinpe.management.model.sys;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 短信、邮件等消息的模板
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/26 10:50
 */
@Data
public class SysMessageDO {
    private Long id;
    private String sceneCode;
    private String usage;
    private String content;
    private LocalDateTime createTime;
}
