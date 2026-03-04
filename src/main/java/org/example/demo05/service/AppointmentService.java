package org.example.demo05.service;

import com.github.pagehelper.Page;
import org.example.demo05.entity.Appointment;

import java.util.List;

public interface AppointmentService {
    List<Appointment> getAppointment(Page<?> page, Appointment appointment);

    Integer addAppointment(Appointment appointment);

    Integer updateAppointment(Appointment appointment);

    Integer cancelAppointment(Integer[] ids);

    Integer attendAppointment(Integer[] ids);

    Integer absentAppointment(Integer[] ids);
}
