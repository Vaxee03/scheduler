package com.example.scheduler.controller;

import com.example.scheduler.dto.ScheduleRequestDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/scheduler")
@RestController
public class ScheduleController {

    private final Map<Integer, Schedule> schedules = new HashMap<>();

    // 스케줄 생성
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto) {

        // NULL 값 여부 체크
        if(dto.getTitle() == null || dto.getDescription() == null || dto.getPassword() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // auto increment
        Integer scheduleId = schedules.isEmpty() ? 1: Collections.max(schedules.keySet())+1;

        // 객체 생성
        Schedule Schedule = new Schedule(scheduleId,dto.getTitle(),dto.getDescription(),dto.getPassword(), LocalDate.now());

        // Inmemory DB에 Schedule을 저장
        schedules.put(scheduleId, Schedule);

        // 생성 성공 반환
        return new ResponseEntity<>(new ScheduleResponseDto(Schedule), HttpStatus.CREATED);
    }


    // 스케줄 전체 조회
    @GetMapping
    public List<ScheduleResponseDto> searchAllSchedules() {

        // 조회 리스트 생성
        List<ScheduleResponseDto> responseList = new ArrayList<>();

        // 조회 리스트에 스케줄 데이터 넣기
        for(Schedule schedule : schedules.values()) {
            ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);
            responseList.add(responseDto);
        }

        // 조회 리스트 반환
        return responseList;
    }

    // ID로 스케줄 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> searchScheduleById(@PathVariable Long id) {

        // 입력한 Id키에 해당하는 데이터를 가져옴
        Schedule schedule = schedules.get(id);

        // NULL 값 여부 체크
        if(schedule == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // 조회 스케줄 반환
        return new ResponseEntity<>(new ScheduleResponseDto(schedule), HttpStatus.OK);
    }

}
