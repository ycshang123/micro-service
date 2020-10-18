package com.soft1851.contentcenter.entity.dto;


import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Table;

/**
 * @author ycshang
 */
@Data
@Builder
@Table(name = "share")
@ApiModel("审核")
public class ShareAuditDto {
    /**
     * 审核状态
     */
    private String auditStatusEnum;
    /**
     * 原因
     */
    private String reason;
    private Boolean showFlag;
}
