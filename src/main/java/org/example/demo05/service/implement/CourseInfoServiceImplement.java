package org.example.demo05.service.implement;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.demo05.dao.CourseInfoDAO;
import org.example.demo05.entity.CourseInfo;
import org.example.demo05.service.CourseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseInfoServiceImplement implements CourseInfoService {
    CourseInfoDAO courseInfoDAO;

    @Autowired
    public void setCourseInfoMapper(CourseInfoDAO courseInfoDAO) {
        this.courseInfoDAO = courseInfoDAO;
    }

    @Override
    public List<CourseInfo> getCourseInfo(Page<?> page, CourseInfo courseInfo) {
        try (Page<?> _ = PageHelper.startPage(page.getPageNum(), page.getPageSize())) {
            return courseInfoDAO.selectAll(courseInfo);
        }
    }

    @Override
    public Integer addCourseInfo(CourseInfo courseInfo) {
        return courseInfoDAO.insert(courseInfo);
    }

    @Override
    public Integer updateCourseInfo(CourseInfo courseInfo) {
        return courseInfoDAO.updateByPrimaryKey(courseInfo);
    }

    @Override
    public Integer deleteCourseInfo(Integer[] ids) {
        return courseInfoDAO.deleteByPrimaryKey(ids);
    }

    @Override
    public List<CourseInfo> getMainInfo() {
        return this.courseInfoDAO.getMainInfo();
    }
}
