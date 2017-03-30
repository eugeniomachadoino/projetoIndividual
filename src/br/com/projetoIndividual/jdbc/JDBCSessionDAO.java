package br.com.projetoIndividual.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.projetoIndividual.jdbcinterface.DAO;
import br.com.projetoIndividual.objects.Session;

public class JDBCSessionDAO implements DAO {
	private Connection connec;

	public JDBCSessionDAO(Connection connec) {
		this.connec = connec;
	}

	public boolean create(Session session) {
		String query = "insert into sessions " + "(session_date, present, session_register)" + "values (?,?,?)";

		PreparedStatement p;
		try {
			p = this.connec.prepareStatement(query);
			p.setDate(1, new java.sql.Date(session.getDate().getTime()));
			p.setBoolean(2, session.isPresent());
			p.setString(3, session.getSession_register());
			p.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public List<Session> listSessions() {
		String query = "select * from sessions ";

		List<Session> listSession = new ArrayList<Session>();
		Session session = null;

		try {
			java.sql.Statement stmt = connec.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				session = new Session();
				int id = rs.getInt("id_session");
				Date sessionDate = rs.getDate("session_date");
				Boolean present = rs.getBoolean("present");
				String sessionReg = rs.getString("session_register");

				session.setId_session(id);
				session.setDate(sessionDate);
				session.setPresent(present);
				session.setSession_register(sessionReg);

				listSession.add(session);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listSession;
	}

	public Session searchById(int id_session) {
		String query = "select * from sessions where id_session = " + id_session;
		Session session = new Session();
		try {
			java.sql.Statement stmt = connec.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("id_session");
				Date date = rs.getDate("session_date");
				boolean present = rs.getBoolean("present");
				String session_register = rs.getString("session_register");

				session.setId_session(id);
				session.setDate(date);
				session.setPresent(present);
				session.setSession_register(session_register);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;
	}

	public boolean deleteSession(int id_session) {
		String query = "delete from sessions where id_session = " + id_session;
		Statement p;
		try {
			p = this.connec.createStatement();
			p.execute(query);

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updateSession(Session session) {
		String query = "Update sessions set session_date=?, present=?, session_register=? where id_session ="
				+ session.getId_session();
		PreparedStatement p;
		try {
			p = this.connec.prepareStatement(query);
			p.setDate(1, new java.sql.Date(session.getDate().getTime()));
			p.setBoolean(2, session.isPresent());
			p.setString(3, session.getSession_register());

			p.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<Session> searchByDate(String dateValue) {
		String query = "select * from sessions where session_date = '" + dateValue + "%'";

		
		/*
		 * String query = "select * from sessions ";
		if (!dateValue.equals("null") && !dateValue.equals("")) {
			query += "where session_date like'" + dateValue + "%'";
		}
		*/
		
		
		List<Session> listDates = new ArrayList<Session>();
		Session session = null;

		try {
			java.sql.Statement stmt = connec.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				session = new Session();
				int id = rs.getInt("id_session");
				Date sessionDate = rs.getDate("session_date");
				Boolean present = rs.getBoolean("present");
				String sessionReg = rs.getString("session_register");

				session.setId_session(id);
				session.setDate(sessionDate);
				session.setPresent(present);
				session.setSession_register(sessionReg);

				listDates.add(session);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listDates;
	}

}
