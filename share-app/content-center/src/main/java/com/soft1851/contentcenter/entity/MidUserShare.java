package com.soft1851.contentcenter.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ycshang
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mid_user_share")
public class MidUserShare {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    @Column(name = "share_id")
    private Integer shareId;
    @Column(name = "user_id")
    private Integer userId;
}
