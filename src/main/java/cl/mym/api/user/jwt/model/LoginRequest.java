package cl.mym.api.user.jwt.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoginRequest implements Serializable {

	private static final long serialVersionUID = -2609152578630482634L;

	@NotBlank(message = "email es obligatorio")
	@NotNull(message = "email es obligatorio")
	private String email;

	@NotBlank(message = "password es obligatorio")
	@NotNull(message = "password es obligatorio")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginRequest other = (LoginRequest) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LoginRequest [email=" + email + ", password=" + password + "]";
	}

}

