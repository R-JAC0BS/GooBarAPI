package br.com.goobar_app.Models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "Comentarios")
@Getter
@Setter
public class ComentarioModel {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private BarModel userComent;

    private String usuario;

    private String comentario;


}
