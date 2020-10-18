package com.soft1851.contentcenter.entity.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author ycshang
 */
@Data
@Builder
@Table(name = "share")
@ApiModel("投稿")
public class ShareRequestDto {
    @Column(name = "user_id")
    @ApiModelProperty(name = "user_id", value = "发布人id")
    private Integer userId;
    @Column(name = "author")
    @ApiModelProperty(name = "author", value = "发布人id")
    private String author;

    @Column(name = "download_url")
    @ApiModelProperty(name = "download_url", value = "下载地址")
    private String downloadUrl;

    @Column(name = "is_original")
    @ApiModelProperty(name = "is_original", value = "是否原创0：否，1：是")
    private Boolean isOriginal;

    @Column(name = "price")
    @ApiModelProperty(name = "price", value = "价格(需要的积分)")
    private Integer price;

    @Column(name = "summary")
    @ApiModelProperty(name = "summary", value = "概要信息")
    private String summary;

    @Column(name = "title")
    @ApiModelProperty(name = "title", value = "标题")
    private String title;


}
