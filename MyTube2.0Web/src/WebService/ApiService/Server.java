package WebService.ApiService;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import WebService.BO.ServerBO;
import WebService.DAO.ServerDAO;

@Path("/server")
public class Server {
	ServerDAO serverDAO = new ServerDAO();
	
	
	/**
     * listAll the list of all servers.
     */
    @Path("/")
    @GET
    @Produces("application/json")
    public List<ServerBO> getAllServers() {
        return serverDAO.getAllServers();
    }
    
    /**
     * Add new server to the DB
     * @param serverBo the new server to add
     * @return result-> the ID of the new Server
     */
    @Path("/new")
    @POST
    public Response connectNewServer(ServerBO serverBO){
    	int result = serverDAO.insertNewServer(serverBO);
        if(result != -1){
        	return Response.status(200).build();
        }else{
        	return Response.status(500).build();
        }
    }
    
	/**
	 * Shows the information of the server with ID serverId
	 * @param serverID
	 * @return the ServerBO object that corresponds to serverID
	 */
    @Path("/{serverID}")
    @GET
    @Produces("application/json")
    public ServerBO getServerInfo(@PathParam("serverID") int serverID){
        return serverDAO.getServerByID(serverID);
    }
    
    /**
	 * Shows the information of the server with ID serverId
	 * @param serverID
	 * @return the ServerBO object that corresponds to serverID
	 */
    @Path("/host/{host}/port/{port}")
    @GET
    @Produces("application/json")
    public ServerBO getServerInfo(@PathParam("host") String host, @PathParam("port") String port){
        return serverDAO.getServerByHostPort(host, port);
    }

   
}
