package com.fst.back_etat_civil.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    
    
    @ManyToOne
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

  
    @ManyToOne
    @JoinColumn(name = "profP_id")
    private Profession professionPere;


    
    private String nomMere;


    @Column( name = "prenom_mere")
    private String prenomMere;
    
    @ManyToOne 
    @JoinColumn(name = "profM_id")
    private Profession professionMere;
    
    @ManyToOne 
    @JoinColumn(name = "lieuNaissance_id")
    private Vqf lieuNaissance;
    
   
    @ManyToOne 
    @JoinColumn(name = "adresse_id")
    private Vqf adresse;
    
    @OneToMany(mappedBy = "citoyen", cascade = CascadeType.ALL)
	@JsonProperty(access = Access.WRITE_ONLY)
    private List<Document> documents;
    
   
    private String rue;


   
    private String porte;
    
    
    private String autre;
    
    private String cle;

}
