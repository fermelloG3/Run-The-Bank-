package io.github.fermelloG3.rest.dto;

public class PaymentDTO {
    private String fromAgency;
    private String toAgency;
    private double amount;

    public PaymentDTO (){}

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
