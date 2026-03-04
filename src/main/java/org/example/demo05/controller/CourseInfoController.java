package org.example.demo05.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.example.demo05.entity.CourseInfo;
import org.example.demo05.service.CourseInfoService;
import org.example.demo05.utils.JsonResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/course-info")
public class CourseInfoController {
    CourseInfoService courseInfoService;

    @Autowired
    public void setCourseInfoService(CourseInfoService courseInfoService) {
        this.courseInfoService = courseInfoService;
    }

    @GetMapping
    public JsonResp getCourseInfo(int page, int limit, CourseInfo courseInfo) {
        try {
            Page<?> p = new Page<>(page, limit);
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
}
