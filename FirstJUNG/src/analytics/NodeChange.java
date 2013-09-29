package analytics;

import java.util.ArrayList;
import java.util.List;

public class NodeChange {
	
	Boolean isNew = false;
	Boolean isDeleted = false;
	Boolean isClass = false;
	Boolean isPackage = false;
/*	
	private Double SLOC;
	private Double PuM;
	private Double ProM;
<<<<<<< HEAD
*/
=======
	private String GMLid = null;
	private String nodeType;
	private Boolean hasChangedSinceLastGraph;
	private List<NodeChange> neighboursThatChanged;
	private List<NodeChange> successors;
	private List<NodeChange> predecessors;
>>>>>>> branch 'development' of https://github.com/gregorymeyer/JUNGModel.git
	
	private String GMLid = null;	
	Metrics metrics;
	List<String> sucNodes = new ArrayList<>();
	List<String> preNodes = new ArrayList<>();
	
<<<<<<< HEAD
	public NodeChange(String gmlid ,Metrics m, List<String> sucs, List<String> pres){
=======
	
	
	public NodeChange(String gmlid, Double slocDiff, Double pumDiff, Double promDiff, String nodeType){
>>>>>>> branch 'development' of https://github.com/gregorymeyer/JUNGModel.git
			
			this.GMLid = gmlid;
<<<<<<< HEAD
			this.metrics = m;
			this.sucNodes = sucs;
			this.preNodes = pres;
		/*	
			if (SLOC == 0.0 & PuM == 0.0 & ProM==0.0){ isPackage=true; }
			else {isClass = true;}
		*/	
=======
			this.SLOC = slocDiff;
			this.PuM = pumDiff;
			this.ProM = promDiff;
			this.nodeType = nodeType;
			this.hasChangedSinceLastGraph = hasNodeChanged();
>>>>>>> branch 'development' of https://github.com/gregorymeyer/JUNGModel.git
		}
	
<<<<<<< HEAD
	public NodeChange(String gmlid, Metrics m, List<String> sucs, List<String> pres, Boolean flag){
=======

	public NodeChange(String gmlid, Double slocDiff, Double pumDiff, Double promDiff, String nodeType, Boolean flag){
>>>>>>> branch 'development' of https://github.com/gregorymeyer/JUNGModel.git
		
		this.GMLid = gmlid;
<<<<<<< HEAD
		this.metrics = m;
		this.sucNodes = sucs;
		this.preNodes = pres;
		/*
		if (SLOC == 0.0 & PuM == 0 & ProM==0){ isPackage=true; }
		else {isClass = true;}
		*/	
=======
		this.SLOC = slocDiff;
		this.PuM = pumDiff;
		this.ProM = promDiff;
		this.nodeType = nodeType;
		
>>>>>>> branch 'development' of https://github.com/gregorymeyer/JUNGModel.git
		if (flag) {isNew = true;}
		else if (!flag) {isDeleted = true;}
		this.hasChangedSinceLastGraph = hasNodeChanged();
	}
	
	private Boolean hasNodeChanged() 
	{
		if((this.SLOC==0) && (this.ProM==0) && (this.PuM==0) && (this.nodeType.equals("CLASSNODE")))
			return false;
		else if(isNew | isDeleted)
			return true;
		else if((this.SLOC==0) && (this.ProM==0) && (this.PuM==0) && (this.nodeType.equals("PACKAGENODE")) && !(isNew | isDeleted))
			return false;
		else return true;
	}
	
	public List<NodeChange> getChangedNeighbours() 
	{
		return this.neighboursThatChanged;
	}
	
	public Boolean isNew(){
		return isNew;
	}
	
	public void setIsNew(Boolean flag){
		isNew = flag;
	}
	
	public Boolean isDeleted(){
		return isDeleted;
	}
	
	public void setIsDeleted(Boolean flag){
		isDeleted = flag;
	}
	
	public Double getSLOC() {
		return metrics.getSLOC();
	}

	public void setSLOC(Double sLOC) {
		metrics.setSLOC(sLOC);
	}

	public Double getPuM() {
		return metrics.getPuM();
	}

	public void setPuM(Double puM) {
		metrics.setPuM(puM);
	}

	public Double getProM() {
		return metrics.getProM();
	}

	public void setProM(Double proM) {
		metrics.setProM(proM);
	}

	public String getGMLid() {
		return GMLid;
	}

	public void setGMLid(String gMLid) {
		GMLid = gMLid;
	}
	
	public String getNodeType()
	{
		return this.nodeType;
	}

	public boolean hasChanged() 
	{
		return this.hasChangedSinceLastGraph;
	}

	public void setChangedNeighbours(List<NodeChange> changedNeighbours) 
	{
		this.neighboursThatChanged = changedNeighbours;	
	}


	public void setSuccessors(List<NodeChange> successors) 
	{
		this.successors = successors;	
	}

	public List<NodeChange> getSuccessors() 
	{
		return this.successors;
	}
	
	public void setPredecessors(List<NodeChange> predecessors) 
	{
		this.predecessors = predecessors;	
	}

	public List<NodeChange> getPredecessors() 
	{
		return this.predecessors;
	}

}
