package cl.mym.api.user.jwt;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import cl.mym.api.user.jwt.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"security.basic.enabled=false" })
public class UserControllerTokenTest {
	@LocalServerPort
	private int port;
	TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();

	

	@Test
	public void testUsersGET() throws Exception {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(UtilTest.createURIWithPort(UtilTest.ULR_API + "users",port), HttpMethod.GET,
				entity, String.class);
		assertTrue(response.getBody().equalsIgnoreCase(UtilTest.MESSAGE_UNAUTHORIZED));
		assertTrue(response.getStatusCode() == HttpStatus.UNAUTHORIZED);
		
	}

	@Test
	public void testUserGet() throws Exception {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(UtilTest.createURIWithPort(UtilTest.ULR_API + "users/" + 1, port),
				HttpMethod.GET, entity, String.class);
		assertTrue(response.getBody().equalsIgnoreCase(UtilTest.MESSAGE_UNAUTHORIZED));
		assertTrue(response.getStatusCode() == HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void testUserPut() throws Exception {

		HttpEntity<User> entity = new HttpEntity<User>(UtilTest.createUserAdminOk(), headers);

		ResponseEntity<?> response = restTemplate.exchange(UtilTest.createURLWithPort(UtilTest.ULR_API + "users/{userId}", port),
				HttpMethod.PUT, entity, String.class, 1);
		assertTrue(response.getStatusCode() == HttpStatus.UNAUTHORIZED);

	}

	@Test
	public void testUserDelete() throws Exception {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(UtilTest.createURIWithPort(UtilTest.ULR_API + "users/" + 1, port),
				HttpMethod.DELETE, entity, String.class);
		
		assertTrue(response.getBody().equalsIgnoreCase(UtilTest.MESSAGE_UNAUTHORIZED));
		assertTrue(response.getStatusCode() == HttpStatus.UNAUTHORIZED);
	}

	
}