package com.redstevo.code.sprinsecurity.Repositories;

import com.redstevo.code.sprinsecurity.Entities.AuthenticationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends CrudRepository<AuthenticationEntity, Long> {

    Optional<AuthenticationEntity> findByUsername(String username);
}
