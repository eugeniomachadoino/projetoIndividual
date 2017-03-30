package br.com.projetoIndividual.jdbcinterface;

import br.com.projetoIndividual.objects.Session;

import java.util.List;

public interface DAO {
	public boolean create(Session session);
	public List<Session> listSessions ();
	public Session searchById(int id);
	public List<Session> searchByDate(String dateValue);
	public boolean deleteSession(int id_Session);
	public boolean updateSession(Session session);
}
