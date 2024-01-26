package io.github.fermelloG3.rest.dto;

import java.math.BigDecimal;

public class PaymentDTO {
    private String fromAgency;
    private String toAgency;
    private BigDecimal amount;

    public PaymentDTO (){}

    public PaymentDTO(String fromAgency, String toAgency, BigDecimal amount) {
        this.fromAgency = fromAgency;
        this.toAgency = toAgency;
        this.amount = amount;
    }

    public String getFromAgency() {
        return fromAgency;
    }

    public void setFromAgency(String fromAgency) {
        this.fromAgency = fromAgency;
    }

    public String getToAgency() {
        return toAgency;
    }

    public void setToAgency(String toAgency) {
        this.toAgency = toAgency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
