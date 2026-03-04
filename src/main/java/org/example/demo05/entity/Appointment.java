package org.example.demo05.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@TableName("cms_appointment")
@Getter
@Setter
@ToString
public class Appointment {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @NotNull(message = "学生ID不可为空")
    private Integer studentId;

    @NotNull(message = "课程信息ID不可为空")
    private Integer courseInfoId;

    private String record;
    private Date recordTime;

    private Student student;
    private CourseInfo courseInfo;
}
