package com.fst.back_etat_civil.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column( name = "id")
    private Long id;


    @NotNull
    @Column( name = "nom")
    private String nom;

    @NotNull
    @Column( name = "type")
    private String type;

    @NotNull
    @Column(name = "date")
    private Date date;

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", date=" + date +
                '}';
    }
}
