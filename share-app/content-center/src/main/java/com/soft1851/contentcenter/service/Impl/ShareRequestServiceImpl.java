package com.soft1851.contentcenter.service.Impl;

import com.soft1851.contentcenter.entity.Share;
import com.soft1851.contentcenter.entity.dto.ShareRequestDto;
import com.soft1851.contentcenter.mapper.ShareMapper;
import com.soft1851.contentcenter.mapper.ShareRequestDtoMapper;
import com.soft1851.contentcenter.service.ShareRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ycshang
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareRequestServiceImpl implements ShareRequestService {
    private final ShareRequestDtoMapper shareRequestDtoMapper;
    private final ShareMapper shareMapper;


    @Override
    public int sharesContribute(ShareRequestDto shareRequestDto) {

        Share share = Share.builder()
                .userId(shareRequestDto.getUserId())
                .title(shareRequestDto.getTitle())
                .isOriginal(shareRequestDto.getIsOriginal())
                .createTime(new Date())
                .updateTime(new Date())
                .author(shareRequestDto.getAuthor())
                .cover("https://img4.mukewang.com/szimg/5d1032ab08719e0906000338-360-202.jpg")
                .summary(shareRequestDto.getSummary())
                .price(shareRequestDto.getPrice())
                .downloadUrl(shareRequestDto.getDownloadUrl())
                .buyCount(0)
                .showFlag(false)
                .auditStatus("NOT_YET")
                .build();
       return shareMapper.insert(share);


    }
}
