package org.example.demo05.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.example.demo05.entity.Course;
import org.example.demo05.entity.CourseInfo;
import org.example.demo05.entity.Teacher;
import org.example.demo05.service.CourseInfoService;
import org.example.demo05.utils.JsonResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/course-info")
public class CourseInfoController {
    CourseInfoService courseInfoService;

    @Autowired
    public void setCourseInfoService(CourseInfoService courseInfoService) {
        this.courseInfoService = courseInfoService;
    }

    @GetMapping
    public JsonResp getCourseInfo(int page, int limit, @RequestParam Map<String, String> params) {
        CourseInfo courseInfo = new CourseInfo();
        Course course = new Course();
        Teacher teacher = new Teacher();
        courseInfo.setCourse(course);
        courseInfo.setTeacher(teacher);
        try {
            Page<?> p = new Page<>(page, limit);

            // 基础字段
            if (params.containsKey("courseDate")) {
                if (params.get("courseDate") != null && !params.get("courseDate").isEmpty()) {
                    courseInfo.setCourseDate(LocalDate.parse(params.get("courseDate")));
                }
            }
            if (params.containsKey("coursePeriod")) {
                if (params.get("coursePeriod") != null && !params.get("coursePeriod").isEmpty()) {
                    courseInfo.setCoursePeriod(Integer.parseInt(params.get("coursePeriod")));
                }
            }
            if (params.containsKey("courseAddress")) {
                if (params.get("courseAddress") != null && !params.get("courseAddress").isEmpty()) {
                    courseInfo.setCourseAddress(params.get("courseAddress"));
                }
            }

            // 处理嵌套字段 - 支持多种格式
            // 1. 点号格式: course.name
            // 2. 方括号格式: course[name]
            params.forEach((key, value) -> {
                if (key.contains("course")) {
                    if (key.endsWith(".courseName") || key.endsWith("[courseName]")) {
                        if (value != null && !value.isEmpty()) {
                            courseInfo.getCourse().setCourseName(value);
                        }
                    } else if (key.endsWith(".courseId") || key.endsWith("[courseId]")) {
                        if (value != null && !value.isEmpty()) {
                            courseInfo.getCourse().setCourseId(value);
                        }
                    }
                }
                if (key.contains("teacher")) {
                    if (key.endsWith(".name") || key.endsWith("[name]")) {
                        if (value != null && !value.isEmpty()) {
                            courseInfo.getTeacher().setName(value);
                        }
                    } else if (key.endsWith(".teacherId") || key.endsWith("[teacherId]")) {
                        if (value != null && !value.isEmpty()) {
                            courseInfo.getTeacher().setTeacherId(value);
                        }
                    }
                }
            });
            List<CourseInfo> courseInfoList = courseInfoService.getCourseInfo(p, courseInfo);
            PageInfo<?> pageInfo = new PageInfo<>(courseInfoList);
            return JsonResp.success(pageInfo);
        } catch (Exception e) {
//            return JsonResp.error(500, e.toString());
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public JsonResp addCourseInfo(@RequestBody CourseInfo courseInfo) {
        try {
            int resp = courseInfoService.addCourseInfo(courseInfo);
            return JsonResp.success(resp);
        } catch (Exception e) {
//            return JsonResp.error(500, e.toString());
            throw new RuntimeException(e);
        }
    }

    @PutMapping
    public JsonResp updateCourseInfo(@RequestBody CourseInfo courseInfo) {
        try {
            int resp = courseInfoService.updateCourseInfo(courseInfo);
            return JsonResp.success(resp);
        } catch (Exception e) {
//            return JsonResp.error(500, e.toString());
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping
    public JsonResp deleteCourseInfo(@RequestBody Integer[] ids) {
        if (ids != null && ids.length == 0) {
            return JsonResp.error(400, "id为空");
        }
        try {
            int res = courseInfoService.deleteCourseInfo(ids);
            return JsonResp.success(res);
        } catch (Exception e) {
//            return JsonResp.error(500, e.toString());
            throw new RuntimeException(e);
        }
    }

    //主要信息，适配前端下拉列表(选择器)
    @GetMapping(path = "/mainInfo")
    public JsonResp getMainInfo() {
        return JsonResp.success(this.courseInfoService.getMainInfo());
    }
}
