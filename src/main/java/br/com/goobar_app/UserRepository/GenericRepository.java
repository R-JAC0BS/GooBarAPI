package br.com.goobar_app.UserRepository;

import br.com.goobar_app.Models.GenericModel;
import br.com.goobar_app.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenericRepository <E extends GenericModel> extends JpaRepository<E, GenericModel> {

}
