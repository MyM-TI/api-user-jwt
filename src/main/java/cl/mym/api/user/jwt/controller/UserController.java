package cl.mym.api.user.jwt.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cl.mym.api.user.jwt.entity.UserEntity;
import cl.mym.api.user.jwt.exception.RecordNotFoundException;
import cl.mym.api.user.jwt.model.ResponseMessage;
import cl.mym.api.user.jwt.model.User;
import cl.mym.api.user.jwt.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired(required = true)
	private UserService userService;

	@GetMapping("/users")
	public List<UserEntity> findAll() {
		return userService.findAll();
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<UserEntity> getUser(@Valid @PathVariable String userId) {
		log.info("init");
		UserEntity user = userService.findById(userId);

		if (user == null) {
			log.info("user not found userId : " + userId);
			throw new RecordNotFoundException("User id not found -" + userId);
		}

		log.info("end");
		log.debug("end user :" + user);
		return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
	}

	@PostMapping("/users/")
	@ResponseBody
	public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
		log.info("init");
		if (userService.existsUserByEmail(user.getEmail().toLowerCase())) {
			ResponseMessage responseMessage = new ResponseMessage("El correo ya registrado");
			log.info("email user exist");
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		UserEntity userEntity = userService.save(user);
		log.info("end");
		log.debug("end result:" + userEntity);
		return new ResponseEntity<UserEntity>(userEntity, HttpStatus.OK);
	}


	@PutMapping("/users/{userId}/")
	public ResponseEntity<?> updateUser(@Valid @PathVariable String userId, @Valid @RequestBody User user) {
		log.info("init");
		UserEntity userUpdate = userService.update(userId, user);
		log.info("end");
		return new ResponseEntity<UserEntity>(userUpdate, HttpStatus.OK);
	}

	@DeleteMapping("/user/{userId}/")
	public ResponseEntity<?> deleteUser(@PathVariable String userId) throws Exception {
		log.info("init");
		String message = userService.delete(userId);
		ResponseMessage responseMessage = new ResponseMessage(message);
		log.info("end");
		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
	}

}

