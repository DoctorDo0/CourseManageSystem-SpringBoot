package org.example.demo05.service.implement;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.demo05.dao.AppointmentDAO;
import org.example.demo05.entity.*;
import org.example.demo05.service.AppointmentService;
import org.example.demo05.utils.JsonResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class AppointmentServiceImplement implements AppointmentService {
    AppointmentDAO appointmentDAO;
    StudentServiceImplement studentServiceImplement;
    CourseInfoServiceImplement courseInfoServiceImplement;

    @Autowired
    public void setAppointmentDAO(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    @Autowired
    public void setStudentServiceImplement(StudentServiceImplement studentServiceImplement) {
        this.studentServiceImplement = studentServiceImplement;
    }

    @Autowired
    public void setCourseInfoServiceImplement(CourseInfoServiceImplement courseInfoServiceImplement) {
        this.courseInfoServiceImplement = courseInfoServiceImplement;
    }

    @Override
    public JsonResp getAppointment(Page<?> page, Map<String, String> params) {
        Appointment appointment = new Appointment();
        Student student = new Student();
        CourseInfo courseInfo = new CourseInfo();
        Course course = new Course();
        Teacher teacher = new Teacher();
        appointment.setStudent(student);
        appointment.setCourseInfo(courseInfo);
        courseInfo.setCourse(course);
        courseInfo.setTeacher(teacher);
        // 处理嵌套字段
        // 1. 点号格式: course.name
        // 2. 方括号格式: course[name]
        params.forEach((key, value) -> {
            if (key.contains("student")) {
                if (key.endsWith(".studentId") || key.endsWith("[studentId]")) {
                    if (value != null && !value.isEmpty()) {
                        appointment.getStudent().setStudentId(value);
                    }
                } else if (key.endsWith(".name") || key.endsWith("[name]")) {
                    if (value != null && !value.isEmpty()) {
                        appointment.getStudent().setName(value);
                    }
                }
            }
            if (key.contains("courseInfo")) {
                if (key.endsWith(".courseDate") || key.endsWith("[courseDate]")) {
                    if (value != null && !value.isEmpty()) {
                        appointment.getCourseInfo().setCourseDate(LocalDate.parse(value));
                    }
                } else if (key.endsWith(".coursePeriod") || key.endsWith("[coursePeriod]")) {
                    if (value != null && !value.isEmpty()) {
                        appointment.getCourseInfo().setCoursePeriod(Integer.valueOf(value));
                    }
                } else if (key.endsWith(".courseAddress") || key.endsWith("[courseAddress]")) {
                    if (value != null && !value.isEmpty()) {
                        appointment.getCourseInfo().setCourseAddress(value);
                    }
                }

                if (key.contains(".course") || key.contains("[course]")) {
                    if (key.endsWith(".courseId") || key.endsWith("[courseId]")) {
                        if (value != null && !value.isEmpty()) {
                            appointment.getCourseInfo().getCourse().setCourseId(value);
                        }
                    } else if (key.endsWith(".courseName") || key.endsWith("[courseName]")) {
                        if (value != null && !value.isEmpty()) {
                            appointment.getCourseInfo().getCourse().setCourseName(value);
                        }
                    }
                } else if (key.contains("teacher") || key.contains("[teacher]")) {
                    if (key.endsWith(".teacherId") || key.endsWith("[teacherId]")) {
                        if (value != null && !value.isEmpty()) {
                            appointment.getCourseInfo().getTeacher().setTeacherId(value);
                        }
                    } else if (key.endsWith(".name") || key.endsWith("[name]")) {
                        if (value != null && !value.isEmpty()) {
                            appointment.getCourseInfo().getTeacher().setName(value);
                        }
                    }
                }
            }
        });
        try (Page<?> _ = PageHelper.startPage(page.getPageNum(), page.getPageSize())) {
            PageInfo<?> pageInfo = new PageInfo<>(appointmentDAO.selectAll(appointment));
            return JsonResp.success(pageInfo);
        }
    }

    @Override
    public JsonResp addAppointment(Appointment appointment) {
        if (!checkout(appointment)) {
            return JsonResp.error("参数错误或不存在");
        } else {
            return JsonResp.success(appointmentDAO.insert(appointment));
        }
    }

    @Override
    public JsonResp updateAppointment(Appointment appointment) {
        if (!checkout(appointment)) {
            return JsonResp.error("参数错误或不存在");
        } else {
            return JsonResp.success(appointmentDAO.update(appointment));
        }
    }

    @Override
    public JsonResp cancelAppointment(Integer[] ids) {
        return JsonResp.success(appointmentDAO.delete(ids));
    }

    @Override
    public JsonResp attendAppointment(Integer[] ids) {
        return JsonResp.success(appointmentDAO.attend(ids));
    }

    @Override
    public JsonResp absentAppointment(Integer[] ids) {
        return JsonResp.success(appointmentDAO.absent(ids));
    }

    //校验数据是否存在
    public Boolean checkout(Appointment appointment) {
        return studentServiceImplement.getByPrimaryKey(appointment.getStudentId()) != null
                && courseInfoServiceImplement.getByPrimaryKey(appointment.getCourseInfoId()) != null;
    }
}
