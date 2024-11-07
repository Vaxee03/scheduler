package com.example.scheduler.dto;

import com.example.scheduler.entity.Schedule;
import lombok.Getter;
import java.time.LocalDate;


@Getter
public class ScheduleResponseDto {

    // 필드 선언
    private Integer id;
    private String title;
    private String description;
    private Long password;
    private LocalDate startPeriod;

    // 생성자
    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.description = schedule.getDescription();
        this.password = schedule.getPassword();
        this.startPeriod = schedule.getStartPeriod();
    }

}
