package br.com.goobar_app.UserRepository;

import br.com.goobar_app.Models.BarModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BarRepository extends JpaRepository<BarModel,UUID> {
    BarModel findByEmail (String email);
    void deleteById(UUID id);

    Optional<BarModel> findById(UUID id);

}
