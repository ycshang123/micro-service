package com.soft1851.contentcenter.controller;


import com.soft1851.contentcenter.auth.CheckLogin;
import com.soft1851.contentcenter.common.ResponseResult;
import com.soft1851.contentcenter.entity.Share;
import com.soft1851.contentcenter.entity.dto.*;
import com.soft1851.contentcenter.service.Impl.ShareServiceImpl;
import com.soft1851.contentcenter.service.ShareRequestService;
import com.soft1851.contentcenter.util.JwtOperator;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ycshang
 */
@Slf4j
@RestController
@RequestMapping("/shares")
@Api(tags = "分享接口", value = "提供分享相关的Rest API")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareController {
    private final ShareServiceImpl shareService;
    private final ShareRequestService shareRequestService;
    private final JwtOperator jwtOperator;
    @ApiOperation(value = "分享列表", notes = "根据shareId查询share详情")
    @GetMapping(value = "/{id}")
    public ResponseResult getAll(@PathVariable Integer id) {
        ShareDto share = shareService.findById(id);
        return new ResponseResult(200, "请求成功", share);
    }
    @GetMapping("/query")
    @ApiOperation(value = "分享列表", notes = "分享列表")
    public List<Share> query(
            @RequestParam(required = false) String title,
            @RequestParam(required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(required = false, defaultValue = "10") Integer pagesSize,
            @RequestHeader(value = "x-Token",required = false) String token) throws Exception {
        if (pagesSize > 100) {
            pagesSize = 100;
        }

        Integer userId = null;
        log.info("++++++++++++++++++++++++++++++++"+token);
        if(!"no-token".equals(token)){
            Claims claims = this.jwtOperator.getClaimsFromToken(token);
            userId = (Integer)claims.get("id");
        }
        else {
            log.info("没有token");
        }
        return this.shareService.query(title, pageNo, pagesSize, userId).getList();
    }

    @PostMapping("/contribute")
    @ApiOperation(value = "投稿", notes = "投稿")
    public int sharesContribute(@RequestBody ShareRequestDto shareRequestDto) {
        return this.shareRequestService.sharesContribute(shareRequestDto);
    }

    @PutMapping(value = "/auditById/{id}")
    private Share auditById(@PathVariable Integer id, @RequestBody ShareAuditDto shareAuditDto) {
        return this.shareService.auditById(id, shareAuditDto);
    }

    @GetMapping(value = "/audit/{id}")
    private Share audit(@PathVariable Integer id, @RequestBody ShareAuditDto shareAuditDto) {
        return this.shareService.audit(id, shareAuditDto);
    }

    @PostMapping("/exchange")
    @CheckLogin
    public Share exchange (@RequestBody ExchangeDto exchangeDto){
        return  this.shareService.exchange(exchangeDto);
    }


    @GetMapping("/exchangeList/{id}")
    public ResponseDto exchangeList(@PathVariable Integer id){
        ResponseDto responseDto = ResponseDto
                .builder()
                .succ(true)
                .data(shareService.exchangeList(id))
                .build();
        return  responseDto;
    }

    @GetMapping("/auditList/{id}")
    public ResponseDto auditList(@PathVariable Integer id){
        ResponseDto responseDto = ResponseDto
                .builder()
                .succ(true)
                .data(shareService.auditList(id))
                .build();
        return  responseDto;
    }
    @GetMapping("/noPassList")
    public ResponseDto noPassList(){
        ResponseDto responseDto = ResponseDto
                .builder()
                .succ(true)
                .data(shareService.notPass())
                .build();
        return  responseDto;
    }
}

