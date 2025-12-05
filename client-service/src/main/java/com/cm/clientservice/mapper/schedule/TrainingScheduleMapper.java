package com.cm.clientservice.mapper.schedule;
import com.cm.clientservice.dto.scheduling.TrainingScheduleDto;
import com.cm.clientservice.model.schedule.TrainingSchedule;

public class TrainingScheduleMapper {

    public static TrainingScheduleDto toDto(TrainingSchedule trainingSchedule) {
        TrainingScheduleDto dto = new TrainingScheduleDto();

        dto.setId(String.valueOf(trainingSchedule.getId()));
        dto.setWorkouts(WorkoutMapper.toDto(trainingSchedule.getWorkouts()));
        return dto;
    }
}