package com.microtech.microtech.dto.request.payment;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateEspeceDto.class, name = "ESPECE"),
        @JsonSubTypes.Type(value = CreateChequeDto.class, name = "CHEQUE"),
        @JsonSubTypes.Type(value = CreateVirementDto.class, name = "VIREMENT")
})
public interface PaymentMethod {
    String reference();
}
