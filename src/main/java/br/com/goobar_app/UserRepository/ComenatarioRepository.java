package br.com.goobar_app.UserRepository;

import br.com.goobar_app.Models.BarModel;
import br.com.goobar_app.Models.ComentarioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComenatarioRepository extends JpaRepository<ComentarioModel, Long> {



}
