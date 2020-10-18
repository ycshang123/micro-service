package com.soft1851.usercenter.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 鉴权注解
 * @author ycshang
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface CheckAuthorization {
    String value();
}
