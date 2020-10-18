package com.soft1851.contentcenter.service;

import com.github.pagehelper.PageInfo;
import com.soft1851.contentcenter.entity.Share;
import com.soft1851.contentcenter.entity.dto.ExchangeDto;
import com.soft1851.contentcenter.entity.dto.ShareAuditDto;
import com.soft1851.contentcenter.entity.dto.ShareDto;

import java.util.List;

/**
 * @author ycshang
 */
public interface ShareService {
    /**
     * 分享详情
     *
     * @param id
     * @return
     */
    ShareDto findById(Integer id);

    /**
     * 根据标题模糊查询某个用户的分享列表数据，title为空则为所有数据，查询结果分页
     *
     * @param title
     * @param pageNo
     * @param pageSize
     * @param userId
     * @return
     */

    PageInfo<Share> query(String title, Integer pageNo, Integer pageSize, Integer userId);

    /**
     * 投稿
     *
     * @param id
     * @param shareAuditDto
     * @return
     */
    Share auditById(Integer id, ShareAuditDto shareAuditDto);

    /**
     * 投稿
     *
     * @param id
     * @param shareAuditDto
     * @return
     */
    Share audit(Integer id, ShareAuditDto shareAuditDto);

    /**
     * 积分兑换资源
     * @param exchangeDto
     * @return
     */
    Share exchange(ExchangeDto exchangeDto);

    /**
     * 兑换列表
     * @param
     * @return
     */
    List<Share> exchangeList(Integer id);

    /**
     * 投稿列表
     * @param
     * @return
     */
    List<Share> auditList(Integer id);

    /**
     *未审核资源
     * @return
     */
    List<Share> notPass();

}
