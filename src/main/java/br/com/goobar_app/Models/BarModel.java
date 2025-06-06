package br.com.goobar_app.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "Bar")
@Getter
@Setter
public class BarModel extends GenericModel {

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserModel user;

    @Column(unique = true, nullable = false, length = 50)
    private String nomebar;


    @Column(nullable = false, length = 255)
    private String descricao;
    @Column(name = "email")
    private String email;
    @Column (length = 255)
    private String imagemurl;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "barModel")
    @JsonManagedReference
    private EnderecoModel endereco;

    @JsonBackReference
    @ManyToMany(mappedBy = "barFavoritos")
    private List<UserModel> usuariosFavoritos;


    @Column()
    private Boolean wifi = false;
    @Column()
    private Boolean tv = false;
    @Column()
    private Boolean arcondicionado = false;
    @Column(name = "estacionamento")
    private Boolean estacionamento = false;
    @Column()
    private Boolean mesabilhar = false;
    @Column()
    private Boolean musicaaovivo = false;
    @Column()
    private Boolean praia = false;
    @Column()
    private Boolean arlivre = false;

    @OneToMany (mappedBy = "userComent", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List <ComentarioModel> comentarios;


    @Column (name = "avaliacao" )
    private Double  avaliacao = 0.0 ;

    @Column (name = "numerodeavaliacao" )
    private Integer numerodeavaliacao = 0;
    {

    }


    @PrePersist
    @PreUpdate
    public void persistBoolean (){
        this.wifi = this.wifi != null ? this.wifi : false;
        this.tv = this.tv != null ? this.tv : false;
        this.arlivre = this.arlivre != null ? this.arlivre : false;
        this.mesabilhar = this.mesabilhar != null ? this.mesabilhar : false;
        this.praia = this.praia != null ? this.praia : false;
        this.musicaaovivo = this.musicaaovivo != null ? this.musicaaovivo : false;
        this.arcondicionado = this.arcondicionado != null ? this.arcondicionado : false;
        this.estacionamento = this.estacionamento != null ? this.estacionamento : false;

    }


}
