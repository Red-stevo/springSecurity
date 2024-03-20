package com.redstevo.code.sprinsecurity.Repositories;

import com.redstevo.code.sprinsecurity.Entities.Tokens;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Tokens, Long> {

    Optional<List<Tokens>> findByTokenAndIsLoggedOut(String token, Boolean isLoggedOut);
}
