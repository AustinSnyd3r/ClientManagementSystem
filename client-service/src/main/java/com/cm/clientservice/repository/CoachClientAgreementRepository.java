package com.cm.clientservice.repository;

import com.cm.clientservice.model.contract.CoachClientAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CoachClientAgreementRepository extends JpaRepository<CoachClientAgreement, UUID> {
    List<CoachClientAgreement> findAllByCoach_Id(UUID id);
    boolean existsCoachClientAgreementByClient_IdAndCoach_Id(UUID client_id, UUID coach_id);
}
