package com.example.Rss_project.dto;


public class MemberDTO {
    private String name;
    private String email;
    private String organization;

    @Override
    public String toString() {
        return "MemberDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", organization='" + organization + '\'' +
                '}';
    }

    public MemberDTO(){

    }
    public MemberDTO(String name, String email, String organization) {
        this.name = name;
        this.email = email;
        this.organization = organization;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getOrganization() {
        return organization;
    }
}