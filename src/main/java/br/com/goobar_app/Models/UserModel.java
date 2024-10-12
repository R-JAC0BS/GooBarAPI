package br.com.goobar_app.Models;


import br.com.goobar_app.ROLE.TypeRole;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "User_table")
@Getter
@Setter
public class UserModel extends GenericModel implements UserDetails {

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column (unique = true, nullable = false, length = 50)
    private String email;

    @Column(unique = true, nullable = false, length = 11)
    private String telefone;

    @Column()
    private String imagemUrl;

    @Column (unique = true, nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    private TypeRole role;


    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_bar_favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "bar_id")
    )
    private List<BarModel> barFavoritos;


    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BarModel> bar;
    @Override
    public String toString() {
        return this.email;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }


}
