package com.soft1851.contentcenter.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ycshang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAddBonusMsgDto {
    /**
     * 为谁加积分
     */
    private Integer userId;
    /**
     * 添加多少积分
     */
    private Integer bonus;
}
