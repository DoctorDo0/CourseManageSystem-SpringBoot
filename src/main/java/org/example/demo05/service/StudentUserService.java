package org.example.demo05.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.demo05.entity.Student;

public interface StudentUserService extends IService<Student> {
    Student findByStudentId(String studentId);
}
