package cl.mym.api.user.jwt.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cl.mym.api.user.jwt.model.JwtResponse;
import cl.mym.api.user.jwt.model.LoginRequest;
import cl.mym.api.user.jwt.service.UserService;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

	@Autowired(required = true)
	private UserService userService;

	@RequestMapping(value = "/login/", method = RequestMethod.POST)
	public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
		String token = userService.existsUserByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
		JwtResponse jwtResponse = new JwtResponse(token);
		return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.OK);
	}

}
