package br.com.moip.jassinaturas.clients.attributes;

public class PaymentStatus {
    private int code;
    private String description;

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "PaymentStatus [code=" + code + ", description=" + description + "]";
    }

}
