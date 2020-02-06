package cl.mym.api.user.jwt;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import cl.mym.api.user.jwt.model.Phone;
import cl.mym.api.user.jwt.model.User;

public class UtilTest {
	
	public static final String ULR_API = "/api-user-jwt/api/";
	public static final String MESSAGE_UNAUTHORIZED = "{\"mensaje\":\"acceso denegado\"}";
	public static final String MESSAGE_MAIL_EXIST = "{\"mensaje\":\"El correo ya registrado\"}";
	public static final String MESSAGE_BAD_PASSWORD = "{\"mensaje\":\"password no cumple requisitos minimos\"}";
	public static final String TOKEN_STRING = "accesToken";
	public static final String MESSAGE_LOGIN_BAD = "{\"mensaje\":\"credenciales incorrectas\"}";
	public static final String MESSAGE_USER_DELETE = "user delete";
	public static final String MESSAGE_USER_DISABLED = "{\"mensaje\":\"usuario desabilitado\"}";
	
	public static User createUserAdminOk() {
        User user = new User();
        user.setEmail("admin@admin.com");
        user.setName("admin");
        user.setPassword("1Hola2");
        user.setPhones(new ArrayList<>());
        user.getPhones().add(createPhone1());
        return user;
    }
	
	public static User createUserSuperAdminOk() {
        User user = new User();
        user.setEmail("superadmin@admin.com");
        user.setName("super admin");
        user.setPassword("1Hola2");
        user.setPhones(new ArrayList<>());
        user.getPhones().add(createPhone1());
        return user;
    }
	
	public static User createUser() {
        User user = new User();
        user.setEmail("user_test@admin.com");
        user.setName("Nombre");
        user.setPassword("1Hola2");
        user.setPhones(new ArrayList<>());
        user.getPhones().add(createPhone1());
        return user;
    }
	
	
	public static User createUserAdminBadPassword() {
        User user = new User();
        user.setEmail("admin@admin.com");
        user.setName("admin");
        user.setPassword("hola");
        user.setPhones(new ArrayList<>());
        user.getPhones().add(createPhone1());
        return user;
    }
	
	public static Phone createPhone1() {
		Phone phone = new Phone();
		phone.setCityCode(2);
		phone.setCountryCode(56);
		phone.setNumber(99999999);
		
		return phone;
	}
	
	public static URI  createURIWithPort(String uri, int port) throws URISyntaxException {
		String url = "http://localhost:" + port + uri + "/";
		return new URI(url);
	}
	
	public static String  createURLWithPort(String uri, int port) {
		return "http://localhost:" + port + uri + "/";
	}

}
