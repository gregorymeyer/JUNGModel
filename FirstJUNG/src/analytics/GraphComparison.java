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
		
		Metrics metrics = new Metrics(slocDiff, pumDiff, promDiff);
		
		List<String> sucs = sucPopulation(oNode, nNode);
		List<String> pres = prePopulation(oNode, nNode);
		
		return new NodeChange(gmlid, metrics, sucs, pres);
	}
	
	
	
private NodeChange populateNodeChange(Vertex node, Boolean flag){
		
		String gmlid = node.getProperty("GMLid");
		
		Double slocDiff = stringToDouble(node.getProperty("SLOC"));		
		Double pumDiff = stringToDouble(node.getProperty("PuM"));	
		Double promDiff = stringToDouble(node.getProperty("ProM"));
		
		Metrics metrics = new Metrics(slocDiff, pumDiff, promDiff);
		
		List<String> sucs = sucPopulation(node, flag);
		List<String> pres = prePopulation(node, flag);
		
		return new NodeChange(gmlid, metrics, sucs, pres, flag);
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
		
		///NEW FUNCTION TO APPEND LIST OF SUCCESSORS AND LIST OF PREDECESSORS
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
		return nodeChanges;
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
	
	private List<String> sucPopulation(Vertex oNode, Vertex nNode){
		
		List<Vertex> oSucs = oldGraph.getSuccessors(oNode);
		List<Vertex> nSucs = newGraph.getSuccessors(nNode);
		List<String> sucs = new ArrayList<>();
		
		for (int i = 0; i < oSucs.size(); i++){
			sucs.add(oSucs.get(i).getProperty("GMLid"));
		}
		
		for (int i = 0; i < nSucs.size(); i++){
			if (!sucs.contains(nSucs.get(i).getProperty("GMLid"))){
				sucs.add(nSucs.get(i).getProperty("GMLid"));
			}
		}
		return sucs;
		
	}
	
	private List<String> prePopulation(Vertex oNode, Vertex nNode){
		
		List<Vertex> oPreds = oldGraph.getPredecessors(oNode);
		List<Vertex> nPreds = newGraph.getPredecessors(nNode);
		List<String> preds = new ArrayList<>();
		
		for (int i = 0; i < oPreds.size(); i++){
			preds.add(oPreds.get(i).getProperty("GMLid"));
		}
		
		for (int i = 0; i < nPreds.size(); i++){
			if (!preds.contains(nPreds.get(i).getProperty("GMLid"))){
				preds.add(nPreds.get(i).getProperty("GMLid"));
			}
		}
		return preds;
	}
	
	private List<String> prePopulation(Vertex node, Boolean flag){
		
		List<String> preds = new ArrayList<>();
		
		if (flag){
			List<Vertex> nPreds = newGraph.getPredecessors(node);
			
			for (int i = 0; i < nPreds.size(); i++){
				preds.add(nPreds.get(i).getProperty("GMLid"));
			}
			return preds;
		}
		
		else if (!flag){
			List<Vertex> oPreds = oldGraph.getPredecessors(node);
			
			for (int i = 0; i < oPreds.size(); i++){
				preds.add(oPreds.get(i).getProperty("GMLid"));
			}
			return preds;
		}
		return null;
	}
	
	private List<String> sucPopulation(Vertex node, Boolean flag){
		
		List<String> sucs = new ArrayList<>();
		
		if (flag){
			List<Vertex> nSucs = newGraph.getSuccessors(node);
			
			for (int i = 0; i < nSucs.size(); i++){
				sucs.add(nSucs.get(i).getProperty("GMLid"));
			}
			return sucs;
		}
		
		else if (!flag){
			List<Vertex> oSucs = oldGraph.getSuccessors(node);
			
			for (int i = 0; i < oSucs.size(); i++){
				sucs.add(oSucs.get(i).getProperty("GMLid"));
			}
			return sucs;
		}
		return null;
	}
	
}
