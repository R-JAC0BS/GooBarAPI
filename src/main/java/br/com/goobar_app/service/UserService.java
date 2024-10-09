package br.com.goobar_app.service;

import br.com.goobar_app.DTOS.AlterUser;
import br.com.goobar_app.Models.UserModel;
import br.com.goobar_app.ROLE.TypeRole;
import br.com.goobar_app.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Primary
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return this.userRepository.findByEmail(email)
               .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email));
    }

    @Autowired
    private PasswordEncoder passwordEncoder ;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private UserModel userModel;

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UserModel saveAcount(UserModel userModel)throws Exception{
        if (userModel.getUsername() == null || userModel.getUsername().isEmpty())
            throw new Exception ("Username cannot be empty");
        if (userModel.getPassword() == null || userModel.getPassword().isEmpty())
            throw new Exception ("Password cannot be empty");
        if (userModel.getEmail() == null || userModel.getEmail().isEmpty())
            throw new Exception ("Email cannot be empty");
        if (userModel.getPassword().length() < 8)
            throw new Exception ("Password must be at least 8 characters");
        if (userModel.getTelefone() == null || userModel.getTelefone().isEmpty())
            throw new Exception ("Numero de telefone invalido");

        userModel.setRole(TypeRole.USUARIO);
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return userRepository.save(userModel);
    }

    public String DeleteAcount (String email)throws Exception{
            Optional<UserModel> user = userRepository.findByEmail(email);
            user.ifPresent(model -> userRepository.delete(model));

      return "Usuario deletado com sucesso";
    }


    public String AlterAcount (String email, AlterUser alterUser) throws Exception{
        Optional<UserModel> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            UserModel userModel = user.get();
            if (alterUser.email()!= null){
                userModel.setEmail(alterUser.email());
            }
            if (alterUser.username()!= null){
                userModel.setUsername(alterUser.username());
            }
            if (alterUser.telefone()!=null){
                userModel.setTelefone(alterUser.telefone());
            }
            if (alterUser.password()!= null){
                userModel.setPassword(passwordEncoder.encode(alterUser.password()));
            }
            this.userRepository.save(userModel);
        }
        return "Usuario alterado com sucesso";
    }

}
