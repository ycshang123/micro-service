package com.soft1851.contentcenter.service;

import com.soft1851.contentcenter.entity.Notice;

/**
 * @author ycshang
 */
public interface NoticeService {
    /**
     * 查询最新公告
     *
     * @return
     */
    Notice getLatest();
}
