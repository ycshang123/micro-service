package com.soft1851.coursecenter.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ycshang
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCenterDto {
    private Integer pkId;
    private String name;
    private String avatar;

}
