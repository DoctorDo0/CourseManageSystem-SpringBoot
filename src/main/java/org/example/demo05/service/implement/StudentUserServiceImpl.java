package org.example.demo05.service.implement;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.demo05.dao.StudentUserMapper;
import org.example.demo05.entity.Student;
import org.example.demo05.service.StudentUserService;
import org.springframework.stereotype.Service;

@Service
public class StudentUserServiceImpl extends ServiceImpl<StudentUserMapper, Student> implements StudentUserService {

    @Override
    public Student findByStudentId(String studentId) {
        return this.getBaseMapper().findByStudentId(studentId);
    }
}
