package br.com.moip.jassinaturas.clients.attributes;

public class Errors {
    private String description;
    private String code;

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Errors [description=" + description + ", code=" + code + "]";
    }

}
