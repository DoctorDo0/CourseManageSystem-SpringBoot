package org.example.demo05.service;

import com.github.pagehelper.Page;
import org.example.demo05.entity.CourseInfo;
import org.example.demo05.utils.JsonResp;

import java.util.List;
import java.util.Map;

public interface CourseInfoService {
    JsonResp getCourseInfo(Page<?> page, Map<String, String> params);

    JsonResp addCourseInfo(CourseInfo courseInfo);

    JsonResp updateCourseInfo(CourseInfo courseInfo);

    JsonResp deleteCourseInfo(Integer[] ids);

    //主要信息，适配前端下拉列表(选择器)
    List<CourseInfo> getMainInfo();

    //课程分组占比(教师数量)，适配前端EChart图表用
    JsonResp getTeacherCountWithSameCourse();

    //获取未预约的课程信息，学生专用
    JsonResp getStudentLessonsAvailable(String studentId);
}
