package com.soft1851.usercenter.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name="user_center")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCenter {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer pkId;
    @Column(name="name")
    private String name;
    @Column(name = "account")
    private String account;
    @Column(name = "password")
    private String password;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "introduction")
    private String introduction;

}
