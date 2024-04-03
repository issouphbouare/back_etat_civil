package com.fst.back_etat_civil.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "citoyen")
@ToString
public class Citoyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;



    
    private String niciv;


   
    private String nom;

    
    private String prenom;


    
    private Date dateNaissance;

    private String telephone;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "prof_id")
    private Profession profession;

    @NotNull
    @Column( name = "genre")
    private String genre;
   
    @Column(name = "portrait")
    private String portrait;;

    @Column( name = "civilite")
    private String civilite;


    
    private String prenomPere;


    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "profP_id")
    private Profession professionPere;


    
    private String nomMere;


    @Column( name = "prenom_mere")
    private String prenomMere;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "profM_id")
    private Profession professionMere;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "lieuNaissance_id")
    private Vqf lieuNaissance;
    
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "adresse_id")
    private Vqf adresse;
    
   
    private String rue;


   
    private String porte;
    
    
    private String autre;
    
    private String cle;

}
