package com.soft1851.contentcenter.service;

import com.soft1851.contentcenter.entity.dto.ShareRequestDto;

/**
 * @author ycshang
 */
public interface ShareRequestService {
    /**投稿
     * @param shareRequestDto
     * @return
     */
    int sharesContribute(ShareRequestDto shareRequestDto);
}
