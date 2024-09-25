package br.com.goobar_app.UserRepository;

import br.com.goobar_app.Models.BarModel;

import java.util.Optional;

public interface BarRepository extends GenericRepository <BarModel>{
    BarModel findByEmail (String email);
}
