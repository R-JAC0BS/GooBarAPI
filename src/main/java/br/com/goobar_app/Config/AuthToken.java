package br.com.goobar_app.Config;

import br.com.goobar_app.Models.UserModel;
import br.com.goobar_app.UserRepository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthToken extends OncePerRequestFilter {

    protected final UserRepository usuarioRepository;


    private UserDetails userDetails;
    public AuthToken(UserRepository usuarioRepository) {
        super();
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = JwtUtils.getJwtFromRequest(request);
        if (jwt != null && JwtUtils.validaJwtToken(jwt)) {
            String email = JwtUtils.getUserNameFromJwtToken(jwt);
            var userDetails = this.usuarioRepository.findByEmail(email).orElse(null);
            var user = this.usuarioRepository.findByEmail(email);
            assert userDetails != null;
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, userDetails.getAuthorities());


            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);

    }


}
