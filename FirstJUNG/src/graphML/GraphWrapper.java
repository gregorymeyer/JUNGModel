package graphML;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.*;

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

    public Graph<Vertex, Edge> getBaseGraph() {
        return this.graph;
    }
    
    public Boolean addVertex(Vertex v){
        return graph.addVertex(v);
    }
    
    public Boolean addEdge(Edge e, Vertex source, Vertex target){
        return graph.addEdge(e, source, target);
    }
  

    @XmlElement(name="node")
    public List<Vertex> getNodes(){
        List<Vertex> myNodeList = new ArrayList<Vertex>(graph.getVertices());
        return myNodeList;
    }
    
    public int getNodeCount(){
    	return graph.getVertexCount();
    }
    
    @XmlElement(name="edge")
    public List<Edge> getEdges(){
        List<Edge> myEdgeList = new ArrayList<Edge>(graph.getEdges());
        return myEdgeList;
    }
    
    public int getEdgeCount(){
    	return graph.getEdgeCount();
    }
	
	public Edge findEdge(Vertex v1, Vertex v2){
		return graph.findEdge(v1, v2);
	}

	public boolean containsEdge(Edge e){
		return graph.containsEdge(e);
	}
	
	public boolean containsNode(Vertex v){
		return graph.containsVertex(v);
	}
	
	public Vertex getDest(Edge e){
		return graph.getDest(e);
	}
	
	public Pair<Vertex> getEndPoints(Edge e){
		return graph.getEndpoints(e);
	}
	
	public List<Edge> getIncidentEdges(Vertex v){
		List<Edge> list = new ArrayList<Edge>(graph.getIncidentEdges(v));
		return list;
	}
	
	public List<Edge> getOutgoingEdges(Vertex v){
		List<Edge> list = new ArrayList<Edge>(graph.getOutEdges(v));
		return list;
	}
	
	public List<Vertex> getNeighbours(Vertex v){
		List<Vertex> list = new ArrayList<Vertex>(graph.getNeighbors(v));
		return list;
	}
	
	public Vertex getSource(Edge e){
		return graph.getSource(e);
	}
	
	public List<Vertex> getSuccessors(Vertex v){
		List<Vertex> list = new ArrayList<Vertex>(graph.getSuccessors(v));
		return list;
	}
	
	public int getSuccessorCount(Vertex v){
		return graph.getSuccessorCount(v);
	}
	
	public List<Vertex> getPredecessors(Vertex v){
		List<Vertex> list = new ArrayList<Vertex>(graph.getPredecessors(v));
		return list;
	}
	
	public int getPredecessorCount(Vertex v){
		return graph.getPredecessorCount(v);
	}
	
	public boolean isDest(Vertex v, Edge e){
		return graph.isDest(v, e);
	}
	
	public boolean isSource(Vertex v, Edge e){
		return graph.isSource(v, e);
	}
	
	public boolean removeEdge(Edge e){
		return graph.removeEdge(e);
	}
	
	public boolean removeNode(Vertex v){
		return graph.removeVertex(v);
	}
	
///////////////////////	
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
	
	
    
    
    
}