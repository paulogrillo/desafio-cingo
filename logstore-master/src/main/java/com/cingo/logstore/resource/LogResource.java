package com.cingo.logstore.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cingo.logstore.entity.Log;
import com.cingo.logstore.repostory.LogRepository;

@Path("log")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LogResource {
	
	@Context
	private HttpServletRequest httpRequest;
    private LogRepository repository = new LogRepository();
	
    @GET
    public List<Log> getLogs() {
    	System.out.println("getLogs....");
    	return this.repository.findAllOrdened();
    }
    //ADICIONAR NOVO LOG
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveLog(Log log) {
    	System.out.println("gravando....");
    	 this.repository.addLog(log);
    }
    //ALTERAR LOG
    @PUT
    public void updateLog(Log log) {
    	System.out.println("update....");
    	 this.repository.updateLog(log);
    }
    //DELETAR LOG
    @DELETE
    @Path("{id}")
    public void deleteLog(@PathParam("id") int id) {
    	System.out.println("delete....");
    	Log log = this.repository.find(id);
    	this.repository.removeLog(log);
    }
}
