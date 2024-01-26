package io.github.fermelloG3.rest.dto;

public class CustomerDTO {

    private String name;
    private String document;
    private String adress;

    private String password;

    public CustomerDTO(){}

    public CustomerDTO(String name, String document) {
        this.name = name;
        this.document = document;
    }

    public CustomerDTO(String name, String document, String adress, String password) {
        this.name = name;
        this.document = document;
        this.adress = adress;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
