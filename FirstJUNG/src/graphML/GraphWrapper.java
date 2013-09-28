/**
 * GraphWrapper class
 * The class wraps a SparseMultigraph 
 * 
 *  @author Greg Meyer & Etai Miller
 *  @version 0.1
 */


package graphML;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;



import javax.xml.bind.annotation.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.Pair;

@XmlRootElement (name="graph")
@XmlType (propOrder={"properties", "nodes", "edges"})
public class GraphWrapper {
    private final Graph<Vertex, Edge> graph;
    
    public GraphWrapper() {
        this.graph = new SparseMultigraph<Vertex, Edge>();
    }
    
    public GraphWrapper(Graph<Vertex, Edge> arg) {
        this.graph = arg;
    }

    /**
     * Returns the wrapped SparseMultigraph 
     * 
     * @return The SparseMultigraph
     */
    public Graph<Vertex, Edge> getBaseGraph() {
        return this.graph;
    }
    
    /**
     * Appends a vertex (node) to the wrapped graph
     * 
     * @param v Vertex to be added
     * @return true if the append was successful, false if it was not
     */
    public Boolean addVertex(Vertex v){
        return graph.addVertex(v);
    }
    
    /**
     * Appends an edge to the wrapped graph
     * 
     * @param e Edge to be appended
     * @param source Vertex that is the edge's source
     * @param target Vertex that is the edge's target 
     * @return true if the append was successful, false if it was not
     */
    public Boolean addEdge(Edge e, Vertex source, Vertex target){
        return graph.addEdge(e, source, target);
    }
  

    /**
     * Returns the vertices/nodes held within the wrapped graph
     * 
     * @return Vertices of the graph
     */
    @XmlElement(name="node")
    public List<Vertex> getNodes(){
        List<Vertex> myNodeList = new ArrayList<Vertex>(graph.getVertices());
        return myNodeList;
    }
    
    /**
     * Returns the number of nodes in the wrapped graph
     * 
     * @return Integer number of nodes in the graph
     */
    public int getNodeCount(){
    	return graph.getVertexCount();
    }
    
    /**
     * Returns the edges that are held in the wrapped graph
     * 
     * @return List of Edges that are held in the graph 
     */
    @XmlElement(name="edge")
    public List<Edge> getEdges(){
        List<Edge> myEdgeList = new ArrayList<Edge>(graph.getEdges());
        return myEdgeList;
    }
    
    /**
     * Returns the number of edges in the wrapped graph
     * 
     * @return Integer number of Edges in the graph
     */
    public int getEdgeCount(){
    	return graph.getEdgeCount();
    }
	
    /**
     * Returns an edge that connects two vertices/nodes
     * 
     * @param v1 Vertex connected by edge
     * @param v2 Vertex connected by edge
     * @return Edge connecting two vertices v1 and v2
     */
	public Edge findEdge(Vertex v1, Vertex v2){
		return graph.findEdge(v1, v2);
	}

	/**
	 * Query if there is an edge in the graph
	 * 
	 * @param e Edge to query 
	 * @return true if the Edge exists in the graph, false if it does not
	 */
	public boolean containsEdge(Edge e){
		return graph.containsEdge(e);
	}
	
	/**
	 * Query if there is a Vertex/Node in the graph
	 * 
	 * @param v Vertex/node to query
	 * @return true if the Vertex exists in the graph, false if it does not
	 */
	public boolean containsNode(Vertex v){
		return graph.containsVertex(v);
	}
	
	/**
	 * Returns the target Vertex of the given edge
	 * 
	 * @param e Edge whose target needs to be known
	 * @return The target Vertex of the given Edge e
	 */
	public Vertex getDest(Edge e){
		return graph.getDest(e);
	}
	
	/**
	 * Returns the pair of Vertices joined by the Egde
	 * 
	 * @param e Edge whose joining vertices are to be known 
	 * @return Vertices joined by the Edge e
	 */
	public Pair<Vertex> getEndPoints(Edge e){
		return graph.getEndpoints(e);
	}
	
	/**
	 * Returns all Edges that terminate at the given Vertex 
	 * 
	 * @param v Vertex whose incoming Edges are to be found 
	 * @return Edges that terminate at the Vertex v
	 */
	public List<Edge> getIncidentEdges(Vertex v){
		List<Edge> list = new ArrayList<Edge>(graph.getIncidentEdges(v));
		return list;
	}
	
	/**
	 * Returns all Edges that originate at the given Vertex
	 * 
	 * @param v Vertex whose outgoing edges are to be found 
	 * @return Edges that originate at the Vertex v
	 */
	public List<Edge> getOutgoingEdges(Vertex v){
		List<Edge> list = new ArrayList<Edge>(graph.getOutEdges(v));
		return list;
	}
	
	/**
	 * Returns all Vertices attached to the given Vertex by an Edge 
	 * 
	 * @param v Vertex whose neighbours are to be found
	 * @return List of Vertices that are the neighbours of a the Vertex v. That is, 
	 * neighbour Vertices are connected to the given Vertex by an Edge.
	 */
	public List<Vertex> getNeighbours(Vertex v){
		List<Vertex> list = new ArrayList<Vertex>(graph.getNeighbors(v));
		return list;
	}
	
	/**
	 * Returns the source of the given Edge 
	 * 
	 * @param e Edge whose source Vertex is to be found
	 * @return Source Vertex of Edge e
	 */
	public Vertex getSource(Edge e){
		return graph.getSource(e);
	}
	
	/**
	 * Returns the successors of the given Vertex
	 * 
	 * @param v Vertex whose successors are to be returned
	 * @return List of successor Vertices to v
	 */
	public List<Vertex> getSuccessors(Vertex v){
		List<Vertex> list = new ArrayList<Vertex>(graph.getSuccessors(v));
		return list;
	}
	
	/**
	 * Returns the number of successor Vertices to a given vertex
	 * 
	 * @param v Vertex whose successors are to be returned
	 * @return Integer number of successor Vertices to v
	 */
	public int getSuccessorCount(Vertex v){
		return graph.getSuccessorCount(v);
	}
	
	/**
	 * Returns the predecessors of a given Vertex 
	 * 
	 * @param v Vertex whose predecessors are to be known
	 * @return List of predecessor Vertices to v
	 */
	public List<Vertex> getPredecessors(Vertex v){
		List<Vertex> list = new ArrayList<Vertex>(graph.getPredecessors(v));
		return list;
	}
	
	/**
	 * Returns the number of predecessor Vertices 
	 * 
	 * @param v Vertex whose number of predecessors is to be known
	 * @return Integer of number of predecessors to v 
	 */
	public int getPredecessorCount(Vertex v){
		return graph.getPredecessorCount(v);
	}
	
	/**
	 * Returns the Boolean result of the query
	 * 
	 * @param v Vertex that is a potential destination 
	 * @param e Edge that potentially terminates at the Vertex 
	 * @return Returns true if the Vertex is the Edge's terminating Vertex
	 */
	public boolean isDest(Vertex v, Edge e){
		return graph.isDest(v, e);
	}
	
	/**
	 * Returns true if the vertex is the Source of the given Edge
	 * 
	 * @param v Vertex to verify is source of Edge
	 * @param e Edge whose originating Vertex is to be known
	 * @return Returns true if Vertex is the source of the Edge  e
	 */
	public boolean isSource(Vertex v, Edge e){
		return graph.isSource(v, e);
	}
	
	/**
	 * Removes the given Edge from the graph
	 * 
	 * @param e Edge to be removed from the graph
	 * @return true if the removal was successful, false otherwise
	 */
	public boolean removeEdge(Edge e){
		return graph.removeEdge(e);
	}
	
	/**
	 * Removes the given Vertex from the graph
	 * 
	 * @param v Vertex to be removed from the graph 
	 * @return true if removal was successful, false otherwise
	 */
	public boolean removeNode(Vertex v){
		return graph.removeVertex(v);
	}
	
///////////////////////	
	
	/**
	 * Returns true/false based on an Edge existing in the given graph. Takes the GMLids of
	 * Vertices that the Edge would connect.
	 * 
	 * @param value1 GMLid of connected Vertex
	 * @param value2 GMLid of connected Vertex
	 * @return true if the edge exists, false otherwise
	 */
	public boolean containsEdge(String value1, String value2) {
		if(value1.equals(value2)) return false;
		
		List<Edge> eList = this.getEdges();
		Boolean isValueContained = false;
		
		for (int i = 0; i < eList.size(); i++){
			isValueContained = eList.get(i).containsValue(value1,value2);
			if (isValueContained == true)
				return isValueContained;
		}
		return isValueContained;
	}

	/**
	 * Returns true/false base on a Vertex/Node existing.  
	 * 
	 * @param value The GMLid of the Node in question
	 * @return true if the node exists, false otherwise
	 */
	public boolean containsNode(String value) {
		List<Vertex> vList = this.getNodes();
		Boolean isValueContained = false;
		
		for (int i = 0; i < vList.size(); i++){
			isValueContained = vList.get(i).containsValue(value);
			if (isValueContained == true)
				return isValueContained;
		}
		return isValueContained;
	}

	/**
	 * Returns true/false based on an Edge existing.
	 * 
	 * @param value The GMLid of the Edge in question
	 * @return true if the Edge exists, false otherwise
	 */
	public boolean containsEdge(String value) {
		List<Edge> eList = this.getEdges();
		Boolean isValueContained = false;
		
		for (int i = 0; i < eList.size(); i++){
			isValueContained = eList.get(i).containsValue(value);
			if (isValueContained == true)
				return isValueContained;
		}
		return isValueContained;
	}

	/**
	 * Returns the Vertex based on GMLid
	 * 
	 * @param name The GMLid of the Vertex to be returned
	 * @return Vertex with the GMLid of name
	 */
	public Vertex getNode(String name)
	{	
		Vertex ret = null;
		List<Vertex> nodes = this.getNodes();
		for(int i = 0; i<nodes.size(); i++)
		{
			if(nodes.get(i).getProperty("GMLid").equals(name))
				ret = nodes.get(i);
		}
		return ret;
	}
	
	/**
	 * Converts every Vertex and Edge object in the graph into JSON objects
	 */
	public void convertToJson(){
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String nodeJson = gson.toJson(graph.getVertices());
		String edgeJson = gson.toJson(graph.getEdges());
		try{
				FileWriter writer = new FileWriter("JSONfile.json");
				writer.write("{\n\"nodes\": " + nodeJson + ",\n\"links\": " + edgeJson + "\n}");
				writer.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * Removes all illegal characters in the GMLids of every node in the graph. Illegal
	 * characters are held in an internal list. 
	 */
	public void removeBadCharacters() 
	{
		List<Vertex> nodes = this.getNodes();
		List<String> badCharacters = new ArrayList<String>();
		badCharacters.add(".");
		badCharacters.add("$");
		
		// Remove bad characters from all nodes
		for(Vertex node : nodes)
		{
			String temp  = node.getProperty("GMLid");
			for(String badChar : badCharacters)
			{
				if(temp.contains(badChar))
				{
					String newGMLid = temp.replace(badChar, "");
					// Overwrite the existing GMLid value
					node.addData("GMLid", newGMLid);
				}
				
			}
		}
	}
}