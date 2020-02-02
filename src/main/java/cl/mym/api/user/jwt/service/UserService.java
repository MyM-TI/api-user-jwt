package cl.mym.api.user.jwt.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import cl.mym.api.user.jwt.entity.UserEntity;
import cl.mym.api.user.jwt.exception.RecordNotFoundException;
import cl.mym.api.user.jwt.model.User;

public interface UserService {
	
	public List<UserEntity> findAll();
	
	public UserEntity findById(String userId) throws RecordNotFoundException;

	public UserEntity save(User user);
	
	public boolean existsUserByEmail(String email);
	
	public UserEntity update(String userId, User user) throws RecordNotFoundException;
	
	public String delete(String userId) throws RecordNotFoundException;
	
	public String existsUserByEmailAndPassword(String email, String password) throws RecordNotFoundException;
	
	public UserEntity findByEmail(String email) throws RecordNotFoundException;
	
	public UserDetails loadUserByEmail(String email) throws RecordNotFoundException;
	
}
