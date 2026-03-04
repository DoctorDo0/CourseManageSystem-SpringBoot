package org.example.demo05.service;

import com.github.pagehelper.Page;
import org.example.demo05.entity.CourseInfo;

import java.util.List;

public interface CourseInfoService {
    List<CourseInfo> getCourseInfo(Page<?> page, CourseInfo courseInfo);

    Integer addCourseInfo(CourseInfo courseInfo);

    Integer updateCourseInfo(CourseInfo courseInfo);

    Integer deleteCourseInfo(Integer[] ids);
}
