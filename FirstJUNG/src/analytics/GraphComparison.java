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
			return nList;
		}
		
		
		else if (oList.size() > nList.size()){
			for (int n = 0; n < oList.size(); n++)
			{
				for (int o = 0; o < oList.size(); o++){
					if (oList.get(o).getProperty("GMLid").equals(nList.get(n).getProperty("GMLid")))
					{
						oList.remove(o);
					}
				}
			}
			return oList;
		}
		return null;
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
			return nList;
		}
		else if (nList.size() >= oList.size()){
			for (int n = 0; n < oList.size(); n++)
			{
				for (int o = 0; o < oList.size(); o++){
					if (oList.get(o).getSource().equals(nList.get(n).getSource())
							& oList.get(o).getTarget().equals(nList.get(n).getTarget()))
					{
						oList.remove(o);
					}
				}
			}
			return oList;
		}
		return null;
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
		
		if (newGraph.getNodes().size() >= oldGraph.getNodes().size()){
			return performAlgOnNew();
		}
			
		else if (newGraph.getNodes().size() < oldGraph.getNodes().size()){
			return performAlgOnOld();
		}
		else return null;	
	}
	
	private NodeChange populateNodeChange(Vertex oNode, Vertex nNode){
		
		String gmlid = oNode.getProperty("GMLid");
		
		Double slocDiff = stringToDoubleDiff(oNode.getProperty("SLOC"), nNode.getProperty("SLOC"));
		Double pumDiff = stringToDoubleDiff(oNode.getProperty("PuM"), nNode.getProperty("PuM"));
		Double promDiff = stringToDoubleDiff(oNode.getProperty("ProM"), nNode.getProperty("ProM"));
		String nodeType = oNode.getProperty("NodeType");
		
		return new NodeChange(gmlid, slocDiff, pumDiff, promDiff,nodeType);
	}
	
	
	
private NodeChange populateNodeChange(Vertex node, Boolean flag){
		
		String gmlid = node.getProperty("GMLid");
		Double slocDiff = stringToDouble(node.getProperty("SLOC"));
		Double pumDiff = stringToDouble(node.getProperty("PuM"));
		Double promDiff = stringToDouble(node.getProperty("ProM"));
		String nodeType = node.getProperty("NodeType");
		
		return new NodeChange(gmlid, slocDiff, pumDiff, promDiff, nodeType, flag);
	}
	
	private List<NodeChange> performAlgOnNew(){
		
		Boolean isOldInNew = false;
		List<Vertex> oList = oldGraph.getNodes();
		List<Vertex> nList = newGraph.getNodes();
		
		for (int o = 0; o < oList.size(); o++)
		{
			for (int n = 0; n < nList.size(); n++){
				if (nList.get(n).getProperty("GMLid").equals(oList.get(o).getProperty("GMLid")))
				{
					isOldInNew = true;
					nodeChanges.add(populateNodeChange(oList.get(o), nList.get(n)));
					nList.remove(n);
				}
			}
			//If old node has been deleted, overload constructor with the false flag,
			//indicating it has been deleted
			if (!isOldInNew){
				nodeChanges.add(populateNodeChange(oList.get(o), isOldInNew));
			}
			isOldInNew = false;
		}
		
		//Run through the remainder of the new nodes and append them to list as being newly added 
		//with the flag overloaded constructor being true
		for (int n = 0; n < nList.size(); n++){
			nodeChanges.add(populateNodeChange(nList.get(n), true));				
		}
		// Find all the neighbours that also changed
		findNeighboursThatChanged();
		return nodeChanges;
	}


	private List<NodeChange> performAlgOnOld(){
		
		Boolean isNewInOld = false;
		List<Vertex> oList = oldGraph.getNodes();
		List<Vertex> nList = newGraph.getNodes();
		
		for (int n = 0; n < nList.size(); n++)
		{
			for (int o = 0; o < oList.size(); o++){
				if (oList.get(o).getProperty("GMLid").equals(nList.get(n).getProperty("GMLid")))
				{
					isNewInOld = true;
					nodeChanges.add(populateNodeChange(oList.get(o), nList.get(n)));
					oList.remove(o);
				}
			}
			//If not in old graph, construct with flag = true for newly added node
			if (!isNewInOld){
				nodeChanges.add(populateNodeChange(nList.get(n), true));
			}
			isNewInOld = false;
		}
		//Run through remainder of old nodes and flag them as deleted
		for (int o = 0; o < oList.size(); o++){
			nodeChanges.add(populateNodeChange(oList.get(o), false));				
		}
		// Find all the neighbours that also changed
		findNeighboursThatChanged();
		return nodeChanges;
	}
	
	private void findNeighboursThatChanged() 
	{
		List<Vertex> neighbours = new ArrayList<Vertex>();
		
		// Find each of the neighbours from the old graph
		for(NodeChange nodeChange : nodeChanges)
		{
			if(nodeChange.hasChanged() && !nodeChange.isNew())
			{
				neighbours = oldGraph.getNeighbours(oldGraph.getNode(nodeChange.getGMLid()));
				nodeChange.setChangedNeighbours(getChangedNeighbours(neighbours));
			}	
		}
	}
	
	private List<NodeChange> getChangedNeighbours(List<Vertex> neighbours) 
	{
		List<NodeChange> ret = new ArrayList<NodeChange>();
		// If a neighbour has also changed, add to the local list
		for(Vertex neighbour : neighbours)
		{
			for(NodeChange nodeChange : nodeChanges)
			{
				if(neighbour.getProperty("GMLid").equals(nodeChange.getGMLid()) && nodeChange.hasChanged())
				{ret.add(nodeChange);}
			}
		}
		return ret;
	}

	private Double stringToDoubleDiff(String oString, String nString){
		Double diff = 0.0;
		Double oVal = 0.0; 
		Double nVal = 0.0;
		if (!oString.isEmpty()){ oVal = Double.parseDouble(oString);}
		if (!nString.isEmpty()){ nVal = Double.parseDouble(nString);}
		
		diff = nVal - oVal;
		return diff;
	}
	
	private Double stringToDouble(String oString){
		Double val = 0.0;
		if (!oString.isEmpty()){ val = Double.parseDouble(oString);}
		
		return val;
	}
	
}
