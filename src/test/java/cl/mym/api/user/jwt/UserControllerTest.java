package cl.mym.api.user.jwt;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;

import cl.mym.api.user.jwt.entity.UserEntity;
import cl.mym.api.user.jwt.model.JwtResponse;
import cl.mym.api.user.jwt.model.LoginRequest;
import cl.mym.api.user.jwt.model.User;
import cl.mym.api.user.jwt.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"security.basic.enabled=false" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {
	
	@LocalServerPort
	private int port;
	TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();
	
	@Autowired
    private UserService userService;
	
	@Test
	public void aCreateUserTest() throws Exception {
		User user = UtilTest.createUser();
		HttpEntity<User> userHttp = new HttpEntity<User>(user, headers);

		ResponseEntity<UserEntity> responseSuccess = restTemplate.exchange(UtilTest.createURIWithPort(UtilTest.ULR_API + "users", port), HttpMethod.POST, userHttp, UserEntity.class);
		
		assertTrue(responseSuccess.getStatusCode() == HttpStatus.OK);
	}
	
	@Test
	public void testUsersGET() throws Exception {
		headers.set("Authorization", login());
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(UtilTest.createURIWithPort(UtilTest.ULR_API + "users",port), HttpMethod.GET,
				entity, String.class);
		
		assertTrue(response.getStatusCode() == HttpStatus.OK);
		
	}
	
	@Test
	public void testUserGet() throws Exception {
		User userCreated = UtilTest.createUser();
		UserEntity userfind = userService.findByEmail(userCreated.getEmail());
		headers.set("Authorization", login());
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<UserEntity> response = restTemplate.exchange(UtilTest.createURIWithPort(UtilTest.ULR_API + "users/" + userfind.getId(), port),
				HttpMethod.GET, entity, UserEntity.class);

		assertTrue(response.getStatusCode() == HttpStatus.OK);
		assertTrue(response.getBody().getEmail().equalsIgnoreCase(userCreated.getEmail()));
		assertTrue(response.getBody().getName().equalsIgnoreCase(userCreated.getName()) );
		
	}
	
	@Test
	public void testUserPut() throws Exception {
		headers.set("Authorization", login());
		User userCreated = UtilTest.createUser();
		UserEntity userForUpdate = userService.findByEmail(userCreated.getEmail());
		User user = new User();
		user.setEmail(userForUpdate.getEmail());
		user.setName("Nuevo Nombre");
		user.setPassword("1Hola2");
		user.setPhones(new ArrayList<>());
		HttpEntity<User> entity = new HttpEntity<User>(user, headers);

		ResponseEntity<UserEntity> response = restTemplate.exchange(UtilTest.createURLWithPort(UtilTest.ULR_API + "users/{userId}", port),
				HttpMethod.PUT, entity, UserEntity.class, userForUpdate.getId());

		assertTrue(response.getStatusCode() == HttpStatus.OK);
		UserEntity userUpdate = userService.findByEmail(userForUpdate.getEmail());
		assertTrue(userUpdate.getName().equalsIgnoreCase(user.getName()));
		assertTrue(response.getBody().getName().equalsIgnoreCase(user.getName()));

	}
	
	
	private String login() throws Exception {
		User user = UtilTest.createUser();
		
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail(user.getEmail());
		loginRequest.setPassword(user.getPassword());
		
		HttpEntity<LoginRequest> loginHttp = new HttpEntity<LoginRequest>(loginRequest, headers);
		
		ResponseEntity<String> responseLoginSuccess = restTemplate.exchange(UtilTest.createURLWithPort(UtilTest.ULR_API + "login", port),
				HttpMethod.POST, loginHttp, String.class);
		assertTrue(responseLoginSuccess.getStatusCode() == HttpStatus.OK);
		Gson g = new Gson();
		JwtResponse jwtResponse = g.fromJson(responseLoginSuccess.getBody(), JwtResponse.class);
		return jwtResponse.getAccesToken();
		
		
	}


}
