package br.com.goobar_app.UserRepository;

import br.com.goobar_app.Models.UserModel;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends GenericRepository <UserModel> {

    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, UUID id);


    Optional<UserModel> findByEmail(String email);

    UserModel findByid(UUID id);

}
