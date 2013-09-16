package graphML;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.io.graphml.EdgeMetadata;
import edu.uci.ics.jung.io.graphml.GraphMLReader2;
import edu.uci.ics.jung.io.graphml.GraphMetadata;
import edu.uci.ics.jung.io.graphml.HyperEdgeMetadata;
import edu.uci.ics.jung.io.graphml.NodeMetadata;



public class GraphmlIO {
	
	/* Create the Graph Transformer */
	Transformer<GraphMetadata, Graph<Vertex, Edge>>
	graphTransformer = new Transformer<GraphMetadata,
	                          Graph<Vertex, Edge>>() {
		
	  public Graph<Vertex, Edge>
	      transform(GraphMetadata metadata) {
	        if (metadata.getEdgeDefault().equals(
	        metadata.getEdgeDefault().DIRECTED)) {
	            return new
	            DirectedSparseMultigraph<Vertex, Edge>();
	        } else return new
	            SparseMultigraph<Vertex, Edge>();       
	      }
	};
	
	VertexFactory vfact= new VertexFactory();
	/* Create the Vertex Transformer */
	Transformer<NodeMetadata, Vertex> 
	vertexTransformer = new Transformer<NodeMetadata, Vertex>() {
	    public Vertex transform(NodeMetadata metadata) {
	        Vertex v = vfact.create(metadata.getProperties(), metadata.getId()); 
	        System.out.println(metadata.toString());
	        return v;
	    }
	};
	
	EdgeFactory efact= new EdgeFactory();
	/* Create the Edge Transformer */
	 Transformer<EdgeMetadata, Edge> edgeTransformer =
	 new Transformer<EdgeMetadata, Edge>() {
	     public Edge transform(EdgeMetadata metadata) {
	         Edge e = efact.create(metadata.isDirected(),metadata.getSource(),
	        		 metadata.getTarget(), metadata.getProperties());
	         return e;
	     }
	 };
	
	 /* Create the Hyperedge Transformer */
	 Transformer<HyperEdgeMetadata, Edge> hyperEdgeTransformer
	 = new Transformer<HyperEdgeMetadata, Edge>() {
	      public Edge transform(HyperEdgeMetadata metadata) {
		         Edge e = efact.create();
	          return e;
	      }
	 };

	public GraphMLReader2 createGraphReader(String src) throws Exception{
		/* Create the graphMLReader2 */
		StringReader fileReader = new StringReader(src);
		GraphMLReader2<Graph<Vertex, Edge>, Vertex, Edge>
			graphReader = new GraphMLReader2<Graph<Vertex, Edge>, Vertex, Edge>
				(fileReader, graphTransformer, vertexTransformer,
								edgeTransformer, hyperEdgeTransformer);
		return graphReader;
	}
	
	public Graph<Vertex, Edge> parse(String arg) throws Exception{
		Graph<Vertex, Edge> graph = new SparseMultigraph<Vertex, Edge>();
			GraphMLReader2<Graph<Vertex, Edge>, Vertex, Edge> reader = createGraphReader(arg);
			graph = reader.readGraph();
		return graph;
	}
	
	public static String readFile(String file) throws IOException {
	    BufferedReader reader = new BufferedReader( new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	        stringBuilder.append( ls );
	    }
	    return stringBuilder.toString();
	}
	
};