package org.example.demo05.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.example.demo05.entity.*;
import org.example.demo05.service.AppointmentService;
import org.example.demo05.utils.JsonResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/appointment", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppointmentController {
    AppointmentService appointmentService;

    @Autowired
    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public JsonResp getAppointments(int page, int limit, @RequestParam Map<String, String> params) {
        Appointment appointment = new Appointment();
        Student student = new Student();
        CourseInfo courseInfo = new CourseInfo();
        Course course = new Course();
        Teacher teacher = new Teacher();
        appointment.setStudent(student);
        appointment.setCourseInfo(courseInfo);
        courseInfo.setCourse(course);
        courseInfo.setTeacher(teacher);
//        {page=1, limit=20, student[studentId]=a, student[name]=a, courseInfo[courseDate]=2026-03-04T16:00:00.000Z, courseInfo[coursePeriod]=1, courseInfo[courseAddress]=a, courseInfo[course][courseId]=a, courseInfo[course][courseName]=a, courseInfo[teacher][teacherId]=a, courseInfo[teacher][name]=a}
        try {
            Page<?> p = new Page<>(page, limit);

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

            List<Appointment> appointmentList = appointmentService.getAppointment(p, appointment);
            PageInfo<?> pageInfo = new PageInfo<>(appointmentList);
            return JsonResp.success(pageInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public JsonResp addAppointment(@RequestBody Appointment appointment) {
        try {
            int resp = appointmentService.addAppointment(appointment);
            return JsonResp.success(resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping
    public JsonResp updateAppointment(@RequestBody Appointment appointment) {
        try {
            int resp = appointmentService.updateAppointment(appointment);
            return JsonResp.success(resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(path = "/cancel")
    public JsonResp cancelAppointment(@RequestBody Integer[] ids) {
        if (ids != null && ids.length == 0) {
            return JsonResp.error(400, "id为空");
        }
        try {
            int res = appointmentService.cancelAppointment(ids);
            return JsonResp.success(res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(path = "/attend")
    public JsonResp attendAppointment(@RequestBody Integer[] ids) {
        if (ids != null && ids.length == 0) {
            return JsonResp.error(400, "id为空");
        }
        try {
            int res = appointmentService.attendAppointment(ids);
            return JsonResp.success(res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(path = "/absent")
    public JsonResp absentAppointment(@RequestBody Integer[] ids) {
        if (ids != null && ids.length == 0) {
            return JsonResp.error(400, "id为空");
        }
        try {
            int res = appointmentService.absentAppointment(ids);
            return JsonResp.success(res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
