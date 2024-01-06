package com.backend.springjwt.models;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nullable
    private String note;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;



    @ManyToOne
    @JoinColumn(name = "document_id")
    @Nullable
    private Document document;

    @OneToOne
    @JoinColumn(name = "document_categorie_id")
    @NotNull
    private DocumentCategorie documentCategorie;


    @Enumerated(EnumType.STRING)
    private DemandStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public Demande(){};

    public Demande(Long id, @Nullable String note, User student, @Nullable Document document, DocumentCategorie documentCategorie, DemandStatus status, Date createdDate) {
        this.id = id;
        this.note = note;
        this.student = student;
        this.document = document;
        this.documentCategorie = documentCategorie;
        this.status = status;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public DemandStatus getStatus() {
        return status;
    }

    public void setStatus(DemandStatus status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public DocumentCategorie getDocumentCategorie() {
        return documentCategorie;
    }

    public void setDocumentCategorie(DocumentCategorie documentCategorie) {
        this.documentCategorie = documentCategorie;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
