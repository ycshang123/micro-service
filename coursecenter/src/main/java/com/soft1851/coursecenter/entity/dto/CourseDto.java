package com.soft1851.coursecenter.entity.dto;


import com.soft1851.coursecenter.entity.CourseCenter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ycshang
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    private CourseCenter courseCenter;
    private UserCenterDto userCenterDto;

}
