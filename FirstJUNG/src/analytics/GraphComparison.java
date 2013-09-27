package analytics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


import graphML.*;

public class GraphComparison {
	
	GraphWrapper newGraph;
	GraphWrapper oldGraph;
	List<NodeChange> nodeChanges = new ArrayList<>();
	
	public GraphComparison(GraphWrapper og, GraphWrapper ng){
		this.newGraph = ng;
		this.oldGraph = og;
	}

	public Boolean isNodesGreaterInNew() {
		return (oldGraph.getNodes().size() < newGraph.getNodes().size());
	}

	public Boolean isEdgesGreaterInNew() {
		return (oldGraph.getEdges().size() < newGraph.getEdges().size());
	}

	public List<Vertex> addedNodes() {
		//List<Edge> edgeDiff = new ArrayList<>();
		List<Vertex> oList = oldGraph.getNodes();
		List<Vertex> nList = newGraph.getNodes();
		
		if (nList.size() >= oList.size()){
			for (int o = 0; o < oList.size(); o++)
			{
				for (int n = 0; n < nList.size(); n++){
					if (nList.get(n).getProperty("GMLid").equals(oList.get(o).getProperty("GMLid")))
					{
						nList.remove(n);
					}
				}
			}
		}
		return nList;
	}
	
	public List<Edge> addedEdges() {
		List<Edge> oList = oldGraph.getEdges();
		List<Edge> nList = newGraph.getEdges();
		
		if (nList.size() >= oList.size()){
			for (int o = 0; o < oList.size(); o++)
			{
				for (int n = 0; n < nList.size(); n++){
					if (nList.get(n).getSource().equals(oList.get(o).getSource())
							& nList.get(n).getTarget().equals(oList.get(o).getTarget()))
					{
						nList.remove(n);
					}
				}
			}
		}
		return nList;
	}

	public boolean isNodeListPresentInNew(List<Vertex> nNodeList) {
		boolean isNodePresent = false;
		boolean isNodeListPresent = true;
		for (int i = 0; i < nNodeList.size(); i++){
			for (int j = 0; j < newGraph.getNodes().size(); j++){
				if(nNodeList.get(i).getProperty("GMLid").equals(newGraph.getNodes().get(j).getProperty("GMLid")))
					isNodePresent = true;
			}
			if(isNodePresent == false){
				isNodeListPresent = false;
				return isNodeListPresent;
			}
			isNodePresent = false;		
		}
		return isNodeListPresent;
	}

	public boolean isNodeListPresentInOld(List<Vertex> nNodeList) {
		boolean isReplicated = false;
		for (int i = 0; i < nNodeList.size(); i++){
			for (int j = 0; j < newGraph.getNodes().size(); j++){
				if(nNodeList.get(i).getProperty("GMLid").equals(oldGraph.getNodes().get(j).getProperty("GMLid")))
					isReplicated = false;
					return isReplicated;
			}
		}
		return isReplicated;
	}

	public List<NodeChange> nodeChanges() {
		
		List<Vertex> oList = oldGraph.getNodes();
		List<Vertex> nList = newGraph.getNodes();
		
		if (nList.size() >= oList.size()){
			for (int o = 0; o < oList.size(); o++)
			{
				for (int n = 0; n < nList.size(); n++){
					if (nList.get(n).getProperty("GMLid").equals(oList.get(o).getProperty("GMLid")))
					{
						String gmlid = nList.get(n).getProperty("GMLid");
						
						Double slocDiff = Double.parseDouble(nList.get(n).getProperty("SLOC"))
											- Double.parseDouble(oList.get(o).getProperty("SLOC"));
						
						Double pumDiff = Double.parseDouble(nList.get(n).getProperty("PuM"))
										- Double.parseDouble(oList.get(o).getProperty("PuM"));
						
						Double promDiff = Double.parseDouble(nList.get(n).getProperty("ProM"))
										- Double.parseDouble(oList.get(o).getProperty("ProM"));
						
						nodeChanges.add(new NodeChange(gmlid, slocDiff, pumDiff, promDiff));
					}
				}
			}
			List<Vertex> aList = addedNodes();
			
			for (int a = 0; a < aList.size(); a++){
				
				String gmlid = aList.get(a).getProperty("GMLid");
				Double slocDiff = Double.parseDouble(aList.get(a).getProperty("SLOC"));
				Double pumDiff = Double.parseDouble(aList.get(a).getProperty("PuM"));
				Double promDiff = Double.parseDouble(aList.get(a).getProperty("ProM"));
				
				nodeChanges.add(new NodeChange(gmlid, slocDiff, pumDiff, promDiff));				
			}
			return nodeChanges;
		}
		else return null;	
	}
	
	
		
	
}
