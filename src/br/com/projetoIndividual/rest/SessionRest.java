package br.com.projetoIndividual.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import br.com.projetoIndividual.db.connection.Connec;
import br.com.projetoIndividual.jdbc.JDBCSessionDAO;
import br.com.projetoIndividual.objects.Session;

@Path("sessionRest")
public class SessionRest extends UtilRest {
	
	public SessionRest() {
	}

	@POST
	@Path("/newSession")
	@Consumes("application/*")
	public Response newSession (String newSess) {

		try {
			Session session = new ObjectMapper().readValue(newSess, Session.class);
			Connec conec = new Connec();
			Connection connec = conec.openConnection();
			JDBCSessionDAO jdbcSession = new JDBCSessionDAO(connec);
			jdbcSession.create(session);
			conec.closeConnection();

			return this.buildResponse("Sessão cadastrada com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/listSessions/")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response listSessions(){
		try{
			List<Session> session = new ArrayList<Session>();
			
			Connec conec = new Connec();
			Connection connec = conec.openConnection();
			JDBCSessionDAO jdbcSession = new JDBCSessionDAO(connec);
			session = jdbcSession.listSessions();
			conec.closeConnection();
			
			return this.buildResponse(session);
		}catch (Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}		
	}
	
	@POST
	@Path("/searchByDate/{dateValue}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response searchByDate(@PathParam("dateValue") String dateValue){
		try{
			List<Session> session = new ArrayList<Session>();
			
			Connec conec = new Connec();
			Connection connec = conec.openConnection();
			JDBCSessionDAO jdbcSession = new JDBCSessionDAO(connec);
			session = jdbcSession.searchByDate(dateValue);
			conec.closeConnection();
			
			return this.buildResponse(session);
		}catch (Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}		
	}
	
	@POST
	@Path("/searchBySessionId/{id_session}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response searchBySessionId(@PathParam("id_session") int id_session){
		try{
			Connec conec = new Connec();
			Connection connec = conec.openConnection();
			JDBCSessionDAO jdbcSession = new JDBCSessionDAO(connec);
			Session session = jdbcSession.searchById(id_session);
			
			return this.buildResponse(session);
		
		} catch (Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@POST
	@Path("/deleteSession/{id_session}")
	@Consumes("application/*")
	public Response deleteSession(@PathParam("id_session") int id_session){
		try{
			Connec conec = new Connec();
			Connection connec = conec.openConnection();
			JDBCSessionDAO jdbcSession = new JDBCSessionDAO(connec);
			jdbcSession.deleteSession(id_session);
			conec.closeConnection();
			
			return this.buildResponse("Sessão excluida com sucesso");
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@POST
	@Path("/updateSession")
	@Consumes("application/*")
	public Response updateSession (String sessionData){
		try{
			Session session = new ObjectMapper().readValue(sessionData, Session.class);
			
			Connec conec = new Connec();
			Connection connec = conec.openConnection();
			JDBCSessionDAO jdbcSession = new JDBCSessionDAO(connec);
			jdbcSession.updateSession(session);
			conec.closeConnection();
		
			return this.buildResponse("Sessão editada com sucesso");
		}catch (Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
}
