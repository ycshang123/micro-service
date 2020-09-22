package com.soft1851.coursecenter.entity;


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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course_center")
@Builder
public class CourseCenter {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer pkId;

    @Column(name = "u_Id")
    private Integer uId;
    @Column(name = "course_avatar")
    private String courseAvatar;
    @Column(name = "course_name")
    private String courseName;
    @Column(name = "course_data")
    private String courseData;
    @Column(name = "course_class")
    private String courseClass;
    @Column(name = "course_number")
    private String courseNumber;
    @Column(name = "is_finished")
    private Boolean isFinished;
}
