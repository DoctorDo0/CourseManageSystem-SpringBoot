package org.example.demo05.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.example.demo05.entity.Appointment;
import org.example.demo05.service.AppointmentService;
import org.example.demo05.utils.JsonResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/appointment", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppointmentController {
    AppointmentService appointmentService;

    @Autowired
    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public JsonResp getAppointments(int page, int limit, Appointment appointment) {
        try {
            Page<?> p = new Page<>(page, limit);
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

    @PutMapping(path = "cancel")
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

    @PutMapping(path = "attend")
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

    @PutMapping(path = "absent")
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
