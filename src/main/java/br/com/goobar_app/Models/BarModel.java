package br.com.goobar_app.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserModel user;

    @Column(unique = true, nullable = false, length = 50)
    private String nomebar;


    @Column(nullable = false, length = 255)
    private String descricao;
    @Column(unique = true, nullable = false, length = 14)
    private String cnpj;
    @Column(unique = true)
    private String email;
    @Column (nullable = false,length = 255)
    private String imagemurl;


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




}
