package cl.mym.api.user.jwt.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -9129148470916015882L;
	
	private String accesToken;

	public JwtResponse(String accesToken) {
		this.accesToken = accesToken;
	}

	public String getAccesToken() {
		return accesToken;
	}

	public void setAccesToken(String accesToken) {
		this.accesToken = accesToken;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accesToken == null) ? 0 : accesToken.hashCode());
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
		JwtResponse other = (JwtResponse) obj;
		if (accesToken == null) {
			if (other.accesToken != null)
				return false;
		} else if (!accesToken.equals(other.accesToken))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JwtResponse [accesToken=" + accesToken + "]";
	}

}

