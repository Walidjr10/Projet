package com.backend.springjwt.models;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class DocumentCategorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String type;


    private String oneTake;


    @NotBlank
    private String description;

    public DocumentCategorie(){};

    public DocumentCategorie(Long id) {
        this.id = id;
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String isOneTake() {
        return oneTake;
    }

    public void setOneTake(String _oneTake) {
        oneTake = _oneTake;
    }
}
