package com.example.scheduler.controller;

import com.example.scheduler.dto.ScheduleRequestDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

        // NULL 값 여부 체크 ( Input )
        if(dto.getTitle() == null || dto.getName() == null || dto.getPassword() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // auto increment
        Integer scheduleId = schedules.isEmpty() ? 1 : Collections.max(schedules.keySet())+1;

        // 객체 생성
        Schedule Schedule = new Schedule(scheduleId,dto.getTitle(),dto.getName(),dto.getPassword(), LocalDate.now());

        // Inmemory DB에 Schedule을 저장
        schedules.put(scheduleId, Schedule);

        // 생성된 스케줄 및 성공 코드 반환
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

        //- [ ]  다음 조건을 바탕으로 등록된 일정 목록을 전부 조회
        //    - [ ]  `수정일` (형식 : YYYY-MM-DD)
        //    - [ ]  `작성자명`
        //- [ ]  조건 중 한 가지만을 충족하거나, 둘 다 충족을 하지 않을 수도, 두 가지를 모두 충족할 수도 있습니다.

        // 수정일 기준 내림차순 정렬


        // 조회 리스트 반환
        return responseList;
    }

    // ID로 스케줄 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> searchScheduleById(@PathVariable Integer id) {

        // 입력한 Id키에 해당하는 데이터를 가져옴
        Schedule schedule = schedules.get(id);

        // NULL 값 여부 체크 ( Schedule Id Key )
        if(schedule == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // 조회된 스케줄 반환
        return new ResponseEntity<>(new ScheduleResponseDto(schedule), HttpStatus.OK);
    }

    // 스케줄 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Integer id, @RequestBody ScheduleRequestDto dto) {

        // 입력한 데이터를 가져옴
        Schedule schedule = schedules.get(id);

        // NULL 값 여부 체크 ( Schedule Id Key )
        if(schedule == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Password 동일 여부 체크
        if(!schedule.getPassword().equals(dto.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // NULL 값 여부 체크 ( Input )
        if(dto.getTitle() == null && dto.getName() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // 제목만 수정하는 경우
        else if(dto.getTitle() != null && dto.getName() == null){
            schedule.updateTitle(dto);
        }

        // 작성자명만 수정하는 경우
        else if(dto.getTitle() == null && dto.getName() != null) {
            schedule.updateName(dto);
        }

        // 둘 다 수정하는 경우
        else if (dto.getTitle() != null && dto.getName() != null) {
            schedule.updateTitle(dto);
            schedule.updateName(dto);
        }

        // 수정 결과 및 성공 코드 반환
        return new ResponseEntity<>(new ScheduleResponseDto(schedule), HttpStatus.OK);
    }

    // 스케줄 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule (@PathVariable Integer id, @RequestBody ScheduleRequestDto dto) {

        // 입력한 데이터를 가져옴
        Schedule schedule = schedules.get(id);

        // NULL 값 여부 체크 ( Schedule Id Key )
        if(schedule == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Password 동일 여부 체크
        else if(!schedule.getPassword().equals(dto.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // 스케줄 삭제
        else {
            schedules.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
