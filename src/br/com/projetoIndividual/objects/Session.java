package br.com.projetoIndividual.objects;

import java.io.Serializable;
import java.util.Date;

public class Session implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id_session;
	private Date date;
	private boolean present;
	private String session_register;
	
	public int getId_session() {
		return id_session;
	}
	public void setId_session(int id_session) {
		this.id_session = id_session;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean isPresent() {
		return present;
	}
	public void setPresent(boolean present) {
		this.present = present;
	}
	
	public String getSession_register() {
		return session_register;
	}
	public void setSession_register(String session_register) {
		this.session_register = session_register;
	}

}
