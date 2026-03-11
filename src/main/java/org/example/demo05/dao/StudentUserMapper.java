package org.example.demo05.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Mapper;
import org.example.demo05.entity.Student;

@Mapper
public interface StudentUserMapper extends BaseMapper<Student> {

    default Student findByStudentId(String studentId) {
        //封装的查询条件
        Wrapper<Student> w = Wrappers.<Student>query()
                .eq("student_id", studentId);
        return selectOne(w);
    }
}
