package br.com.goobar_app.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "Bar")
@Getter
@Setter
public class BarModel extends GenericModel {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserModel user;

    @Column(unique = true, nullable = false, length = 50)
    private String nomebar;


    @Column(nullable = false, length = 255)
    private String descricao;
    @Column(name = "email")
    private String email;
    @Column (nullable = false,length = 255)
    private String imagemurl;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "barModel")
    @JsonManagedReference
    private EnderecoModel endereco;

    @Column()
    private Boolean wifi = false;
    @Column()
    private Boolean tv = false;
    @Column()
    private Boolean arcondicionado = false;
    @Column()
    private Boolean estacionamento = false;
    @Column()
    private Boolean mesabilhar = false;
    @Column()
    private Boolean musicaaovivo = false;
    @Column()
    private Boolean praia = false;
    @Column()
    private Boolean arlivre = false;


    @Column (name = "avaliacao" )
    private Double  avaliacao = 0.0 ;

    @Column (name = "numerodeavaliacao" )
    private Integer numerodeavaliacao = 0;



}
