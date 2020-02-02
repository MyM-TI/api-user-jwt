package cl.mym.api.user.jwt.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.mym.api.user.jwt.entity.PhoneEntity;
import cl.mym.api.user.jwt.entity.UserEntity;
import cl.mym.api.user.jwt.exception.ApiException;
import cl.mym.api.user.jwt.exception.RecordNotFoundException;
import cl.mym.api.user.jwt.model.User;
import cl.mym.api.user.jwt.repository.UserRepository;
import cl.mym.api.user.jwt.secutiry.JwtTokenProvider;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${jwt.secret}")
	private String secret;

	@Override
	public List<UserEntity> findAll() {
		List<UserEntity> listUsers = userRepository.findAll();
		return listUsers;
	}

	@Override
	public UserEntity findById(String userId) throws RecordNotFoundException {
		UserEntity user = userRepository.findById(userId)
				.orElseThrow(() -> new RecordNotFoundException("User id not found -" + userId));
		return user;
	}

	@Override
	public UserEntity save(User user) {
		UserEntity userEntity = parseDTOToUserEntity(user);

		userEntity.setToken(jwtTokenProvider.createToken(userEntity.getEmail()));
		userEntity.setLastLogin(new Date());
		userEntity.setIsActive(true);
		return userRepository.save(userEntity);

	}

	private UserEntity parseDTOToUserEntity(User user) {

		UserEntity userEntity = new UserEntity();
		userEntity.setName(user.getName());
		userEntity.setEmail(user.getEmail());
		userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
		userEntity.getPhones().addAll(getPhonesUser(user));

		return userEntity;
	}

	private Collection<? extends PhoneEntity> getPhonesUser(User user) {
		return user.getPhones().stream()
				.map(phone -> new PhoneEntity(phone.getNumber(), phone.getCityCode(), phone.getCountryCode()))
				.collect(Collectors.toList());
	}

	@Override
	public boolean existsUserByEmail(String email) {

		return userRepository.existsUserByEmail(email);

	}

	@Override
	public UserEntity update(String userId, User user) throws RecordNotFoundException {
		UserEntity userEntity = userRepository.findById(userId)
				.orElseThrow(() -> new RecordNotFoundException("User id not found -" + userId));

		userEntity.setEmail(user.getEmail());
		userEntity.setName(user.getName());
		userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

		return userRepository.save(userEntity);

	}

	@Override
	public String existsUserByEmailAndPassword(String email, String password) throws RecordNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email)
				.orElseThrow(() -> new RecordNotFoundException("User email not found -" + email));

		if (passwordEncoder.matches(password, userEntity.getPassword())) {
			return jwtTokenProvider.createToken(email);
		}

		throw new RecordNotFoundException("credenciales incorrectas");
	}

	@Override
	public String delete(String userId) throws RecordNotFoundException {
		UserEntity userEntity = userRepository.findById(userId)
				.orElseThrow(() -> new RecordNotFoundException("User id not found -" + userId));
		try {
			userRepository.delete(userEntity);

		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ApiException("no delete userId : " + userId);
		}
		return "user delete";
	}

	@Override
	public UserEntity findByEmail(String email) throws RecordNotFoundException {
		UserEntity user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RecordNotFoundException("User id not found -" + email));
		return user;
	}
	
	@Override
	public UserDetails loadUserByEmail(String email) throws RecordNotFoundException {
		final UserEntity userEntity = userRepository.findByEmail(email)
				.orElseThrow(() -> new RecordNotFoundException("User email not found -" + email));

		return org.springframework.security.core.userdetails.User//
				.withUsername(userEntity.getEmail()).password(userEntity.getPassword()).authorities(new ArrayList<>())
				.accountExpired(false).accountLocked(false).credentialsExpired(false).disabled(false).build();
	}

}

