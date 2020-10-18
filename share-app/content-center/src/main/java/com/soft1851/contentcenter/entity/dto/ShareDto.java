package com.soft1851.contentcenter.entity.dto;


import com.soft1851.contentcenter.entity.Share;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("分享详情，带发布人昵称")
public class ShareDto {
    @ApiModelProperty(name = "share", value = "分享资源信息")
    private Share share;
    @ApiModelProperty(name = "wxNickname", value = "发布人昵称")
    private String wxNickname;
}
