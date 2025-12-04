package com.cm.clientservice.service;
import com.cm.clientservice.model.contract.CoachClientAgreement;
import com.cm.clientservice.repository.CoachClientAgreementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CoachClientAgreementService {

    private final CoachClientAgreementRepository coachClientAgreementRepository;

    public CoachClientAgreementService(CoachClientAgreementRepository coachClientAgreementRepository){
        this.coachClientAgreementRepository = coachClientAgreementRepository;
    }

    public List<CoachClientAgreement> getCoachClientAgreements(UUID coachId){

        return coachClientAgreementRepository.findAllByCoach_Id(coachId);
    }

    public boolean isUserAClientOfCoach(UUID userId, UUID coachId){
        return coachClientAgreementRepository
            .existsCoachClientAgreementByClient_IdAndCoach_Id(userId, coachId);
    }

    public boolean isAgreementActive(CoachClientAgreement coachClientAgreement) {
        return coachClientAgreement.getCoachIsInAgreement() &&
                coachClientAgreement.getClientIsInAgreement();
    }

}
