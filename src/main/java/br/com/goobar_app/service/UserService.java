package br.com.goobar_app.service;

import br.com.goobar_app.CustomException.UserException;
import br.com.goobar_app.CustomException.UserStatus;
import br.com.goobar_app.DTOS.AlterPasswordDto;
import br.com.goobar_app.DTOS.AlterUser;
import br.com.goobar_app.DTOS.RegisterDto;
import br.com.goobar_app.Models.BarModel;
import br.com.goobar_app.Models.UserModel;
import br.com.goobar_app.ROLE.TypeRole;
import br.com.goobar_app.Strategy.AccountValidation;
import br.com.goobar_app.Strategy.Validation.AlterPassWord;
import br.com.goobar_app.Strategy.Validation.AlterUserValidationn;
import br.com.goobar_app.UserRepository.BarRepository;
import br.com.goobar_app.UserRepository.UserRepository;
import br.com.goobar_app.components.ExtractEmail;
import br.com.goobar_app.components.Validadores;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Primary
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private BarRepository barRepository;
    private final List <AccountValidation> accountValidations;
    @Autowired
    private PasswordEncoder passwordEncoder ;

    public final  AlterPassWord alterPassWord;
    private final UserModel userModel = new UserModel();
    AlterUserValidationn validation = new AlterUserValidationn();


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email)
               .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email));
    }


    public UserService(UserRepository userRepository, List<AccountValidation> accountValidations, PasswordEncoder passwordEncoder, AlterPassWord alterPassWord) {
        this.userRepository = userRepository;
        this.accountValidations = accountValidations;
        this.passwordEncoder = passwordEncoder;
        this.alterPassWord = alterPassWord;
    }


    public UserModel saveAcount(RegisterDto registerDto)throws Exception{
        BeanUtils.copyProperties(registerDto, userModel);
        accountValidations.forEach(valida -> valida.execute(registerDto));

        userModel.setRole(TypeRole.USUARIO);
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return userRepository.save(userModel);
    }

    public void DeleteAcount (String email)throws Exception{
            Optional<UserModel> user = userRepository.findByEmail(email);
            user.ifPresent(model -> userRepository.delete(model));
    }


    public void AlterAcount(String email, AlterUser alterUser) throws Exception {
        Optional<UserModel> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            UserModel userModel = user.get();
            validation.AlterUser(alterUser);
            userModel.setUsername(alterUser.username());
            userModel.setEmail(alterUser.email());
            userModel.setTelefone(alterUser.telefone());

            this.userRepository.save(userModel);
        } else {
            throw new UserException(UserStatus.USER_NOT_FOUND);
        }
    }


    public void FavoriteBar(UUID id, String email) throws Exception {
        Optional<BarModel> barModelOptional = barRepository.findById(id);
        Optional<UserModel> userOptional = userRepository.findByEmail(email);

        if (barModelOptional.isPresent() && userOptional.isPresent()) {
            BarModel barModel = barModelOptional.get();
            UserModel userModel = userOptional.get();
            if (userModel.getBarFavoritos() == null) {
                userModel.setBarFavoritos(new ArrayList<>());
            }
            if (!userModel.getBarFavoritos().contains(barModel)) {
                userModel.getBarFavoritos().add(barModel);


                barRepository.save(barModel);
                userRepository.save(userModel);


            } else {
                throw new UserException(UserStatus.USER_ERROR_FAVORITE);
            }
        }

    }

    @SneakyThrows
    public void UserAlterPassWord (AlterPasswordDto alter){
        Optional <UserModel> user = userRepository.findByEmail(ExtractEmail.extrairEmail());
        alterPassWord.PassWordValidation(alter, user.get());
        user.get().setPassword(passwordEncoder.encode(alter.newpassword()));
    }
}
