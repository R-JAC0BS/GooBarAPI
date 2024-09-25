package br.com.goobar_app.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;


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

    @Column(unique = true)
    private String email;

    @Column(unique = true, nullable = false, length = 255)
    private String descricao;

    @Column (nullable = false,length = 255)
    private String imagemurl;

    @Column(unique = true, nullable = false, length = 14)
    private String cnpj;
    @Column(unique = true)
    private Boolean wifi = false;
    @Column(unique = true)
    private Boolean tv = false;
    @Column(unique = true)
    private Boolean arcondicionado = false;
    @Column(unique = true)
    private Boolean estacionamento = false;
    @Column(unique = true)
    private Boolean mesabilhar = false;
    @Column(unique = true)
    private Boolean musicaaovivo = false;
    @Column(unique = true)
    private Boolean praia = false;
    @Column(unique = true)
    private Boolean arlivre = false;

}
