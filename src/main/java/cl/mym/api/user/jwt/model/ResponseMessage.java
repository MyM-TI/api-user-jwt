package cl.mym.api.user.jwt.model;

import java.io.Serializable;

public class ResponseMessage implements Serializable {

	private static final long serialVersionUID = 6172270048234731124L;

	public ResponseMessage(String mensaje) {
		super();
		this.mensaje = mensaje;
	}

	private String mensaje;

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mensaje == null) ? 0 : mensaje.hashCode());
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
		ResponseMessage other = (ResponseMessage) obj;
		if (mensaje == null) {
			if (other.mensaje != null)
				return false;
		} else if (!mensaje.equals(other.mensaje))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResponseMessage [mensaje=" + mensaje + "]";
	}

}
