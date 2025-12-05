package com.cm.clientservice.service;

import com.cm.clientservice.dto.contract.template.AgreementTemplateRequestDto;
import com.cm.clientservice.dto.contract.template.AgreementTemplateResponseDto;
import com.cm.clientservice.exception.contract.AgreementTemplateNotFoundException;
import com.cm.clientservice.mapper.contract.AgreementTemplateMapper;
import com.cm.clientservice.model.User;
import com.cm.clientservice.model.contract.AgreementTemplate;
import com.cm.clientservice.repository.AgreementTemplateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AgreementTemplateService {

    private final AgreementTemplateRepository agreementTemplateRepository;

    public AgreementTemplateService(AgreementTemplateRepository agreementTemplateRepository) {
        this.agreementTemplateRepository = agreementTemplateRepository;
    }

    public AgreementTemplateResponseDto createAgreementTemplate(User author,
                                AgreementTemplateRequestDto templateRequestDto){

        AgreementTemplate template =
                AgreementTemplateMapper.toModel(templateRequestDto);

        template.setAuthor(author);
        template.setVersion(1);

        AgreementTemplate createdAgreement =
                agreementTemplateRepository.save(template);

        return AgreementTemplateMapper.toDto(createdAgreement);
    }

    public List<AgreementTemplateResponseDto> findAgreementsAuthoredByCoach(User coach) {
        List<AgreementTemplate> templates = agreementTemplateRepository.findAllByAuthor_Id(coach.getId());
        return AgreementTemplateMapper.toDto(templates);
    }

    public AgreementTemplate findTemplateForContractUse(UUID id) {
        return agreementTemplateRepository.findById(id).orElseThrow(
                () -> new AgreementTemplateNotFoundException("Agreement template not found with id: " + id));
    }
}
