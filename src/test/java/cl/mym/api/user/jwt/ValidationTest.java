package cl.mym.api.user.jwt;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
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
import cl.mym.api.user.jwt.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
"security.basic.enabled=false" })
public class ValidationTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	TestRestTemplate restTemplate;
	HttpHeaders headers = new HttpHeaders();

	@Test
	public void emailExistTest() throws Exception {
		User user = UtilTest.createUserSuperAdminOk();
		HttpEntity<User> userHttp = new HttpEntity<User>(user, headers);

		ResponseEntity<UserEntity> responseSuccess = restTemplate.exchange(UtilTest.createURIWithPort(UtilTest.ULR_API + "users", port), HttpMethod.POST, userHttp, UserEntity.class);

		assertTrue(responseSuccess.getStatusCode() == HttpStatus.OK);
		assertTrue(responseSuccess.getBody().getEmail().equalsIgnoreCase(user.getEmail()));
		assertTrue(responseSuccess.getBody().getName().equalsIgnoreCase(user.getName()) );
		assertTrue(responseSuccess.getBody().getId() != null);
		assertTrue(responseSuccess.getBody().getToken() != null);
		assertTrue(responseSuccess.getBody().getIsActive() == true);
		assertTrue(responseSuccess.getBody().getCreated() != null);
		assertTrue(responseSuccess.getBody().getLastLogin() != null);
		
		ResponseEntity<String> responseFail = restTemplate.exchange(UtilTest.createURIWithPort(UtilTest.ULR_API + "users", port),
				HttpMethod.POST, userHttp, String.class);
		
		assertTrue(responseFail.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY);
		assertTrue(responseFail.getBody().equalsIgnoreCase(UtilTest.MESSAGE_MAIL_EXIST));
		
	}
	
	@Test
	public void passwordBadTest() throws Exception {
		User user = UtilTest.createUserAdminBadPassword();
		HttpEntity<User> userHttp = new HttpEntity<User>(user, headers);
		
		ResponseEntity<String> responseFail = restTemplate.exchange(UtilTest.createURIWithPort(UtilTest.ULR_API + "users", port),
				HttpMethod.POST, userHttp, String.class);
		
		assertTrue(responseFail.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY);
		assertTrue(responseFail.getBody().equalsIgnoreCase(UtilTest.MESSAGE_BAD_PASSWORD));
	}

}
