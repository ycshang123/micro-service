package com.soft1851.coursecenter.service;


import com.soft1851.coursecenter.entity.CourseCenter;
import com.soft1851.coursecenter.entity.dto.CourseDto;
import com.soft1851.coursecenter.entity.dto.UserCenterDto;
import com.soft1851.coursecenter.mapper.CourseCenterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ycshang
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseCenterService {
    private final RestTemplate restTemplate;
    private final CourseCenterMapper courseCenterMapper;

    public List<CourseDto> findAll() {

        List<CourseCenter> list = courseCenterMapper.selectAll();
        List<CourseDto> courseDtoList = new ArrayList<>();
        list.forEach(
                courseCenter -> {
                    int uId = courseCenter.getUId();
                    UserCenterDto userCenterDto = restTemplate.getForObject(
                            "http://120.26.184.121:9001/api/user/{uId}", UserCenterDto.class, uId);
                    assert userCenterDto != null;
                    CourseDto courseDto = CourseDto.builder().courseCenter(courseCenter).userCenterDto(userCenterDto).build();
                    courseDtoList.add(courseDto);
                }
        );
        return courseDtoList;
    }
}
