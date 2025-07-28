package br.com.literAlura.dto;


public class AuthorDTO {

    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
