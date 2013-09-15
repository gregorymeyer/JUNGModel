package graphML;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;

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
    
    @XmlElement(name="edge")
    public List<Edge> getEdges(){
        List<Edge> myEdgeList = new ArrayList<Edge>(graph.getEdges());
        return myEdgeList;
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

	public Boolean containsEdge(String value) {
		List<Edge> eList = this.getEdges();
		Boolean isValueContained = false;
		
		for (int i = 0; i < eList.size(); i++){
			isValueContained = eList.get(i).containsValue(value);
			if (isValueContained == true)
				return isValueContained;
		}
		return isValueContained;
	}
	
	public Boolean containsEdge(String value1, String value2) {
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

    
    
    
    
}