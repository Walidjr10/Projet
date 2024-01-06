package com.backend.springjwt.models;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String type;
    @NotBlank
    private String title;
    private String filePath;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private DocumentCategorie categorie;


    public Document(){};

    public Document(Long id, String type, String title, String filePath, DocumentStatus status, DocumentCategorie categorie) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.filePath = filePath;
        this.status = status;
        this.categorie = categorie;
    }


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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    public DocumentCategorie getCategorie() {
        return categorie;
    }

    public void setCategorie(DocumentCategorie categorie) {
        this.categorie = categorie;
    }
}
