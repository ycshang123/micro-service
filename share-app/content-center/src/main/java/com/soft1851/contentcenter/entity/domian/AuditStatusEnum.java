package com.soft1851.contentcenter.entity.domian;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ycshang
 */

@Getter
@AllArgsConstructor
public enum AuditStatusEnum {
    /**
     * 待审核
     */
    NOT_YET,
    /**
     * 审核通过
     */
    PASS,
    /**
     * 审核不通过
     */
    REJECT,

}
