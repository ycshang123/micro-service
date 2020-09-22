package com.soft1851.usercenter.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soft1851.usercenter.entity.UserCenter;
import com.soft1851.usercenter.entity.dto.UserCenterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author ycshang
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserCenterService {
    private final RestTemplate restTemplate;


    public UserCenterDto getUserDto(int Id){
        Object object=  restTemplate.getForObject("http://127.0.0.1:5000/user/{Id}",Object.class,Id);
        ObjectMapper mapper = new ObjectMapper();
        UserCenter userCenter= mapper.convertValue(object, new TypeReference<UserCenter>() {});
        UserCenterDto userCenterDto = UserCenterDto
                .builder().pkId(userCenter.getPkId())
                .name(userCenter.getName())
                .avatar(userCenter.getAvatar())
                .build();
        return  userCenterDto;
    }
}
