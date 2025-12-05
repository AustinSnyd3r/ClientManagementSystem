package com.cm.clientservice.repository;

import com.cm.clientservice.model.schedule.TrainingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrainingScheduleRepository extends JpaRepository<TrainingSchedule, UUID> {
}
