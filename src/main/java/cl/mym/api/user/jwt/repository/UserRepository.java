package cl.mym.api.user.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.mym.api.user.jwt.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
	
	boolean existsUserByEmail(@Param("email") String email);
	
	Optional<UserEntity> findByEmail(@Param("email") String email);
	
}

