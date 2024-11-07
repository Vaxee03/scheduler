package com.example.scheduler.dto;

import com.example.scheduler.entity.Schedule;
import lombok.Getter;
import java.time.LocalDate;


@Getter
public class ScheduleResponseDto {

    // 필드 선언
    private Integer id;
    private String title;
    private String name;
    private Long password;
    private LocalDate date;

    // 생성자
    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.name = schedule.getName();
        this.password = schedule.getPassword();
        this.date = schedule.getDate();
    }

}
