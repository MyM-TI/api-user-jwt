package cl.mym.api.user.jwt;

import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
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

import cl.mym.api.user.jwt.entity.UserEntity;
import cl.mym.api.user.jwt.model.LoginRequest;
import cl.mym.api.user.jwt.model.User;
import cl.mym.api.user.jwt.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"security.basic.enabled=false" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthenticationControllerTest {
	
	@LocalServerPort
	private int port;
	TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();
	
	@Autowired
    private UserService userService;
	
	@Test
	public void aCreateUserTest() throws Exception {
		User user = UtilTest.createUserAdminOk();
		HttpEntity<User> userHttp = new HttpEntity<User>(user, headers);

		ResponseEntity<UserEntity> responseSuccess = restTemplate.exchange(UtilTest.createURIWithPort(UtilTest.ULR_API + "users", port), HttpMethod.POST, userHttp, UserEntity.class);

		assertTrue(responseSuccess.getStatusCode() == HttpStatus.OK);
	}

	@Test
	public void userNotFound() throws Exception {

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail("user@user.com");
		loginRequest.setPassword("1Hola2");

		HttpEntity<LoginRequest> loginHttp = new HttpEntity<LoginRequest>(loginRequest, headers);

		ResponseEntity<String> responseLoginSuccess = restTemplate.exchange(
				UtilTest.createURLWithPort(UtilTest.ULR_API + "login", port), HttpMethod.POST, loginHttp, String.class);

		assertTrue(responseLoginSuccess.getStatusCode() == HttpStatus.NOT_FOUND);
		assertTrue(responseLoginSuccess.getBody().equalsIgnoreCase(UtilTest.MESSAGE_LOGIN_BAD));
	}
	
	@Test
	public void badPassword() throws Exception {

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail("admin@admin.com");
		loginRequest.setPassword("1Hola22");

		HttpEntity<LoginRequest> loginHttp = new HttpEntity<LoginRequest>(loginRequest, headers);

		ResponseEntity<String> responseLoginSuccess = restTemplate.exchange(
				UtilTest.createURLWithPort(UtilTest.ULR_API + "login", port), HttpMethod.POST, loginHttp, String.class);

		assertTrue(responseLoginSuccess.getStatusCode() == HttpStatus.NOT_FOUND);
		assertTrue(responseLoginSuccess.getBody().equalsIgnoreCase(UtilTest.MESSAGE_LOGIN_BAD));
	}
	
	@Test
	public void badRequestTest() throws Exception {

		HttpEntity<LoginRequest> loginHttp = new HttpEntity<LoginRequest>(null, headers);

		ResponseEntity<String> responseLoginSuccess = restTemplate.exchange(
				UtilTest.createURLWithPort(UtilTest.ULR_API + "login", port), HttpMethod.POST, loginHttp, String.class);

		assertTrue(responseLoginSuccess.getStatusCode() == HttpStatus.BAD_REQUEST);

	}
	
	
	@AfterAll
	public void loginOKTest() throws Exception {
		User user = UtilTest.createUserAdminOk();
		
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail(user.getEmail());
		loginRequest.setPassword(user.getPassword());
		
		HttpEntity<LoginRequest> loginHttp = new HttpEntity<LoginRequest>(loginRequest, headers);
		
		ResponseEntity<String> responseLoginSuccess = restTemplate.exchange(UtilTest.createURLWithPort(UtilTest.ULR_API + "login", port),
				HttpMethod.POST, loginHttp, String.class);
		
		assertTrue(responseLoginSuccess.getStatusCode() == HttpStatus.OK);
		assertTrue(responseLoginSuccess.getBody().contains(UtilTest.TOKEN_STRING));
		
		
	}
	
	@AfterAll
	public void loginBadTest() throws Exception {
		User user = UtilTest.createUserAdminOk();
		
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail(user.getEmail());
		loginRequest.setPassword("111111");
		
		HttpEntity<LoginRequest> loginHttp = new HttpEntity<LoginRequest>(loginRequest, headers);
		
		ResponseEntity<String> responseLoginSuccess = restTemplate.exchange(UtilTest.createURLWithPort(UtilTest.ULR_API + "login", port),
				HttpMethod.POST, loginHttp, String.class);
		
		assertTrue(responseLoginSuccess.getStatusCode() == HttpStatus.NOT_FOUND);
		assertTrue(responseLoginSuccess.getBody().equalsIgnoreCase(UtilTest.MESSAGE_LOGIN_BAD));
		
		
	}
	
	@Test
	public void userDisabled() throws Exception {

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail("admin@admin.com");
		loginRequest.setPassword("1Hola2");
		
		UserEntity userEntity = userService.findByEmail(loginRequest.getEmail());
		userService.delete(userEntity.getId());

		HttpEntity<LoginRequest> loginHttp = new HttpEntity<LoginRequest>(loginRequest, headers);

		ResponseEntity<String> responseLoginSuccess = restTemplate.exchange(
				UtilTest.createURLWithPort(UtilTest.ULR_API + "login", port), HttpMethod.POST, loginHttp, String.class);

		assertTrue(responseLoginSuccess.getStatusCode() == HttpStatus.NOT_FOUND);
		assertTrue(responseLoginSuccess.getBody().equalsIgnoreCase(UtilTest.MESSAGE_USER_DISABLED));
	}
}
