package com.cm.clientservice.service;

import com.cm.clientservice.dto.scheduling.TrainingScheduleDto;
import com.cm.clientservice.mapper.schedule.TrainingScheduleMapper;
import com.cm.clientservice.model.schedule.TrainingSchedule;
import com.cm.clientservice.repository.TrainingScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class TrainingScheduleService {

    private final TrainingScheduleRepository trainingScheduleRepository;

    public TrainingScheduleService(TrainingScheduleRepository trainingScheduleRepository){
        this.trainingScheduleRepository = trainingScheduleRepository;
    }

    public TrainingScheduleDto saveSchedule(TrainingSchedule trainingSchedule){
        TrainingSchedule newSchedule = trainingScheduleRepository.save(trainingSchedule);
        return TrainingScheduleMapper.toDto(newSchedule);
    }
}
