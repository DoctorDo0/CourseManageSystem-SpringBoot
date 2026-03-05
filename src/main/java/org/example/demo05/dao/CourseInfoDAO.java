package org.example.demo05.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.demo05.entity.CourseInfo;

import java.util.List;

@Mapper
public interface CourseInfoDAO {
    List<CourseInfo> selectAll(CourseInfo courseInfo);

    int insert(CourseInfo courseInfo);

    int updateByPrimaryKey(CourseInfo courseInfo);

    int deleteByPrimaryKey(@Param("ids") Integer[] ids);

    List<CourseInfo> getMainInfo();
}
