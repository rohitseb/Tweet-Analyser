package org.ir.proj4.summarization;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ir.proj4.controller.ReclusterController;
import org.ir.proj4.controller.ClusterController;
import org.ir.proj4.newRepVO.NodeVO;
import org.ir.proj4.newRepVO.ReculsterVO;


	@Path("recluster")
	public class Recluster {
	  
	    @POST
	    @Path("/cluster/{id}")
	    @Consumes(MediaType.APPLICATION_JSON+ ";charset=UTF-8")
	    @Produces(MediaType.APPLICATION_JSON+ ";charset=UTF-8")
	    public List<NodeVO> postIt(@PathParam(value = "id") final String id,List<NodeVO> graph) {
	    	//System.out.println(graph);
	    	ReclusterController rec = new ReclusterController();
	    	System.out.println("ID="+id);
	    	return rec.recluster(id,graph);
	    }
	}