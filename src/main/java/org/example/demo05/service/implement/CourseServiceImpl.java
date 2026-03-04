package org.example.demo05.service.implement;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.demo05.dao.CourseDAO;
import org.example.demo05.entity.Course;
import org.example.demo05.service.CourseService;
import org.example.demo05.utils.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@CacheConfig(cacheNames = "courses")
@Service
public class CourseServiceImpl extends ServiceImpl<CourseDAO, Course> implements CourseService {
    private CourseDAO courseDAO;

    @Autowired
    public void setCourseMapper(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Override
    public List<Course> findAll(Page<Course> page) {
        return this.courseDAO.selectList(page, null);
    }

    @Cacheable(key = "'all'")
    @Override
    public List<Course> findAll() {
        return this.courseDAO.selectList(null);
    }

    @Cacheable(key = "#id")
    @Override
    public Course findById(Integer id) {
        return this.courseDAO.selectById(id);
    }

    @CacheEvict(allEntries = true)
    @Override
    public boolean save(Course course) {
        return this.courseDAO.insert(course) > 0;
    }

    @CacheEvict(allEntries = true)
    @Override
    public boolean update(Course course) {
        return this.courseDAO.updateById(course) > 0;
    }

    @CacheEvict(allEntries = true)
    public int deleteByIds(Integer[] ids, AuditEntity auditEntity) {
        return this.courseDAO.deleteByIds(ids, auditEntity);
    }

    @CacheEvict(allEntries = true)
    public int restoreByIds(Integer[] ids, AuditEntity auditEntity) {
        return this.courseDAO.restoreByIds(ids, auditEntity);
    }
}
