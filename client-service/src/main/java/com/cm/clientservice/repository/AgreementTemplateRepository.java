package com.cm.clientservice.repository;

import com.cm.clientservice.model.contract.AgreementTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import java.util.UUID;

public interface AgreementTemplateRepository extends JpaRepository<AgreementTemplate, UUID> {

    List<AgreementTemplate> findAllByAuthor_Id(UUID authorId);
}
