package org.ir.proj4.summarization;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ir.proj4.controller.ClusterController;
import org.ir.proj4.newRepVO.Graph;
import org.ir.proj4.newRepVO.NodeVO;
import org.ir.proj4.vo.SubTopic;
import org.ir.proj4.vo.VizualizationVO;

@Path("query")
public class Cluster {
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("/{query}")
    @Produces(MediaType.APPLICATION_JSON+ ";charset=UTF-8")
    public List<NodeVO> getIt(@PathParam(value = "query") final String query) {
    	ClusterController test= new ClusterController();
    	return test.cluster(query).getGraph();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String postIt(List<NodeVO> graph) {
    	System.out.println(graph);
        return "hello world";
    }
}
