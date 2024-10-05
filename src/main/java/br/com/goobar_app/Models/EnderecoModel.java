package br.com.goobar_app.Models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "location")
@Getter
@Setter
public class EnderecoModel extends GenericModel{


    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "bar_id" )
    @JsonBackReference
    private BarModel barModel;

    private Double latitude;

    private Double longitude;
}
