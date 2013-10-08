package analytics;

import java.util.ArrayList;
import java.util.List;

public class NodeChange {
	
	Boolean isNew = false;
	Boolean isDeleted = false;
	Boolean isClass = false;
	Boolean isPackage = false;
	private Double SLOC;
	private Double PuM;
	private Double ProM;

	private String nodeType;
	private Boolean hasChangedSinceLastGraph;
	private List<NodeChange> neighboursThatChanged;
	private List<NodeChange> successors;
	private List<NodeChange> predecessors;
	
	private String GMLid = null;	
	Metrics metrics;
	
	public NodeChange(String gmlid ,Metrics m ){}
	
	public NodeChange(String gmlid, Metrics m, String nodeType){
			
			this.GMLid = gmlid;
			this.metrics = m;
			this.nodeType = nodeType;
			this.hasChangedSinceLastGraph = hasNodeChanged();
		}

	public NodeChange(String gmlid, Metrics m, String nodeType, Boolean flag){
		
		this.GMLid = gmlid;
		this.metrics = m;
	
		this.nodeType = nodeType;
		
		if (flag) {isNew = true;}
		else if (!flag) {isDeleted = true;}
		this.hasChangedSinceLastGraph = hasNodeChanged();
	}
	
	private Boolean hasNodeChanged() 
	{
		if((metrics.getSLOC()==0) && (metrics.getPuM()==0) && (metrics.getProM()==0) && (this.nodeType.equals("CLASSNODE")))
			return false;
		else if(isNew || isDeleted)
			return true;
		else if((metrics.getSLOC()==0) && (metrics.getPuM()==0) && (metrics.getProM()==0) && (this.nodeType.equals("PACKAGENODE")) && !(isNew | isDeleted))
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
