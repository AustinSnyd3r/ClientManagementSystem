package com.cm.clientservice.model.contract;

import com.cm.clientservice.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Setter
@Getter
public class AgreementTemplate {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank(message = "Template name must be provided.")
    private String templateName;

    @NotNull(message = "Original author must be included for agreement template.")
    @ManyToOne
    @JoinColumn(name = "coach_profile_id")
    private User author;

    @NotNull(message = "Need to specify if author will allow public template reuse.")
    private Boolean allowPublicTemplateReuse;

    @NotNull
    @PositiveOrZero(message = "Payment amount must be positive or zero. ")
    private Double paymentAmount;

    @Positive(message = "Days between payments should be positive. ")
    private Integer daysBetweenPayments;

    @NotBlank(message = "Terms and conditions must not be blank. ")
    private String termsAndConditions;

    @NotNull(message = "Agreement template version must not be null.")
    private Integer version;

}
