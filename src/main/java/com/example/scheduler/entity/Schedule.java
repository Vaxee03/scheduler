package com.example.scheduler.entity;

import com.example.scheduler.dto.ScheduleRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;


@Getter
@AllArgsConstructor
public class Schedule {

    private Integer id;
    private String title;
    private String description;
    private Long password;
    private LocalDate startPeriod;

    public void updateTitle(ScheduleRequestDto dto) {
        this.title = dto.getTitle();
    }

    public void updateDescription(ScheduleRequestDto dto) {
        this.description = dto.getDescription();
    }

    public void update(ScheduleRequestDto dto) {
        this.title = dto.getTitle();
        this.description = dto.getDescription();
    }
}
