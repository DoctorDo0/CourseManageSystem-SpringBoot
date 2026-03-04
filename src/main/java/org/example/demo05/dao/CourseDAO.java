package org.example.demo05.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.example.demo05.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.example.demo05.utils.AuditEntity;

@Mapper
public interface CourseDAO extends BaseMapper<Course> {
    //pass
    int deleteByIds(@Param("ids") Integer[] ids, @Param("auditEntity") AuditEntity auditEntity);

    int restoreByIds(@Param("ids") Integer[] ids, @Param("auditEntity") AuditEntity auditEntity);
}
