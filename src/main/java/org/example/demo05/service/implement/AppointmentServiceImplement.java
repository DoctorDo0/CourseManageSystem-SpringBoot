package org.example.demo05.service.implement;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.demo05.dao.AppointmentDAO;
import org.example.demo05.entity.Appointment;
import org.example.demo05.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImplement implements AppointmentService {
    AppointmentDAO appointmentDAO;

    @Autowired
    public void setAppointmentDAO(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    @Override
    public List<Appointment> getAppointment(Page<?> page, Appointment appointment) {
        try (Page<?> _ = PageHelper.startPage(page.getPageNum(), page.getPageSize())) {
            return appointmentDAO.selectAll(appointment);
        }
    }

    @Override
    public Integer addAppointment(Appointment appointment) {
        return appointmentDAO.insert(appointment);
    }

    @Override
    public Integer updateAppointment(Appointment appointment) {
        return appointmentDAO.update(appointment);
    }

    @Override
    public Integer cancelAppointment(Integer[] ids) {
        return appointmentDAO.delete(ids);
    }

    @Override
    public Integer attendAppointment(Integer[] ids) {
        return appointmentDAO.attend(ids);
    }

    @Override
    public Integer absentAppointment(Integer[] ids) {
        return appointmentDAO.absent(ids);
    }
}
