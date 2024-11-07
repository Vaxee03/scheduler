package com.example.scheduler.entity;

import com.example.scheduler.dto.ScheduleRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;


@Getter
@AllArgsConstructor
public class Schedule {

    // 필드 선언
    private Integer id;
    private String title;
    private String name;
    private Long password;
    private LocalDate date;

    //생성자
    public void updateTitle(ScheduleRequestDto dto) {
        this.title = dto.getTitle();
    }

    public void updateName(ScheduleRequestDto dto) {
        this.name = dto.getName();
    }

    public void update(ScheduleRequestDto dto) {
        this.title = dto.getTitle();
        this.name = dto.getName();
    }
}
