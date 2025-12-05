package com.cm.clientservice.service;
import com.cm.clientservice.dto.contract.CoachClientAgreementRequestDto;
import com.cm.clientservice.dto.contract.CoachClientAgreementResponseDto;
import com.cm.clientservice.dto.contract.ContractAgreementStatusRequestDto;
import com.cm.clientservice.mapper.contract.CoachClientAgreementMapper;
import com.cm.clientservice.model.User;
import com.cm.clientservice.model.contract.AgreementTemplate;
import com.cm.clientservice.model.contract.CoachClientAgreement;
import com.cm.clientservice.repository.CoachClientAgreementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CoachClientAgreementService {

    private final CoachClientAgreementRepository coachClientAgreementRepository;
    private final AgreementTemplateService agreementTemplateService;

    public CoachClientAgreementService(CoachClientAgreementRepository coachClientAgreementRepository,
                                       AgreementTemplateService agreementTemplateService){
        this.coachClientAgreementRepository = coachClientAgreementRepository;
        this.agreementTemplateService = agreementTemplateService;
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

    public CoachClientAgreementResponseDto proposeCoachClientAgreement(User coach, User client, CoachClientAgreementRequestDto agreementDto) {

        CoachClientAgreement agreement = CoachClientAgreementMapper.toModel(agreementDto);
        agreement.setCoach(coach);
        agreement.setClient(client);

        agreement.setAgreementTemplate(agreementTemplateService.findTemplateForContractUse(UUID.fromString(agreementDto.getTemplate().getId())));
        coachClientAgreementRepository.save(agreement);

        return CoachClientAgreementMapper.toDto(agreement);
    }

    public CoachClientAgreementResponseDto withdrawCoachesAgreementAcceptance(CoachClientAgreement agreementToChange) {
        //TODO: Alter this to mirror the client one.
        agreementToChange.setCoachIsInAgreement(Boolean.FALSE);
        CoachClientAgreement updatedAgreement = coachClientAgreementRepository.save(agreementToChange);
        return CoachClientAgreementMapper.toDto(updatedAgreement);
    }

    public Boolean isAgreementPending(CoachClientAgreement agreement){
        return agreement.getClientIsInAgreement().equals(Boolean.FALSE);
    }

    public CoachClientAgreementResponseDto setClientAgreementStatus(CoachClientAgreement agreementToChange,
                                                                    ContractAgreementStatusRequestDto statusUpdateRequest){

        agreementToChange.setClientIsInAgreement(statusUpdateRequest.getUserIsInAgreement());
        CoachClientAgreement updatedAgreement = coachClientAgreementRepository.save(agreementToChange);
        return CoachClientAgreementMapper.toDto(updatedAgreement);
    }

}
