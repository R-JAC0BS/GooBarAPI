package br.com.goobar_app.components;

import br.com.goobar_app.Models.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final UserModel user; // O seu modelo de usuário

    public CustomUserDetails(UserModel user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Aqui você pode retornar as autoridades do usuário, por exemplo, roles
        return null;// Supondo que você tenha um método getRoles() em UserModel
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // Retorna a senha do usuário
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // Retorna o email como username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Você pode implementar lógica aqui
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Você pode implementar lógica aqui
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Você pode implementar lógica aqui
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled(); // Retorna se o usuário está habilitado
    }

    public String getEmail() {
        return user.getEmail(); // Método adicional para obter o email
    }
}
