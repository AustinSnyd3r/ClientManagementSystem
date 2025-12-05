package com.cm.clientservice.service;

import com.cm.clientservice.dto.scheduling.TrainingScheduleDto;
import com.cm.clientservice.dto.scheduling.workout.WorkoutRequestDto;
import com.cm.clientservice.exception.schedule.TrainingScheduleNotFoundException;
import com.cm.clientservice.exception.schedule.WorkoutNotFoundException;
import com.cm.clientservice.mapper.schedule.TrainingScheduleMapper;
import com.cm.clientservice.mapper.schedule.WorkoutMapper;
import com.cm.clientservice.model.schedule.TrainingSchedule;
import com.cm.clientservice.model.schedule.Workout;
import com.cm.clientservice.repository.TrainingScheduleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TrainingScheduleService {

    private final TrainingScheduleRepository trainingScheduleRepository;

    public TrainingScheduleService(TrainingScheduleRepository trainingScheduleRepository){
        this.trainingScheduleRepository = trainingScheduleRepository;
    }

    @Transactional
    public TrainingScheduleDto removeWorkoutFromSchedule(TrainingSchedule schedule, UUID workoutId){
        List<Workout> workouts = schedule.getWorkouts();

        Workout workoutToRemove =
                workouts.stream()
                        .filter(w -> w.getId().equals(workoutId))
                        .findFirst()
                        .orElseThrow(
                                () -> new WorkoutNotFoundException("Workout not found with id: " + workoutId));

        workouts.remove(workoutToRemove);

        TrainingSchedule updatedSchedule = trainingScheduleRepository.save(schedule);
        return TrainingScheduleMapper.toDto(updatedSchedule);
    }

    @Transactional
    public TrainingScheduleDto addWorkoutToSchedule(WorkoutRequestDto workoutDto, UUID trainingScheduleId){
        Workout workout = WorkoutMapper.toModel(workoutDto);

        TrainingSchedule trainingSchedule = trainingScheduleRepository.findById(trainingScheduleId).orElseThrow(
                () -> new TrainingScheduleNotFoundException("Training schedule not found with id: " + trainingScheduleId));

        workout.setTrainingSchedule(trainingSchedule);
        trainingSchedule.getWorkouts().add(workout);

        TrainingSchedule updatedSchedule = trainingScheduleRepository.save(trainingSchedule);
        return TrainingScheduleMapper.toDto(updatedSchedule);
    }
}

