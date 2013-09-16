package analytics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import graphML.*;

public class GraphComparison {
	
	GraphWrapper newGraph;
	GraphWrapper oldGraph;
	
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

	public List<Vertex> nodeListDifference() {
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
	
	public List<Edge> edgeListDifference() {
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
	
	
	
	
	
	
}
