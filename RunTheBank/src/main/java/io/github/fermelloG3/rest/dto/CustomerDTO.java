package io.github.fermelloG3.rest.dto;

public class CustomerDTO {

    private String name;
    private String document;

    public CustomerDTO(){}

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

}
