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
	private String GMLid = null;
	private String nodeType;
	private Boolean hasChangedSinceLastGraph;
	private List<NodeChange> neighboursThatChanged;
	
	List<String> nodeGroup = new ArrayList<>();
	
	public NodeChange(String gmlid, Double slocDiff, Double pumDiff, Double promDiff, String nodeType){
			
			this.GMLid = gmlid;
			this.SLOC = slocDiff;
			this.PuM = pumDiff;
			this.ProM = promDiff;
			this.nodeType = nodeType;
			this.hasChangedSinceLastGraph = hasNodeChanged();
		}
	

	public NodeChange(String gmlid, Double slocDiff, Double pumDiff, Double promDiff, String nodeType, Boolean flag){
		
		this.GMLid = gmlid;
		this.SLOC = slocDiff;
		this.PuM = pumDiff;
		this.ProM = promDiff;
		this.nodeType = nodeType;
		
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
		return SLOC;
	}

	public void setSLOC(Double sLOC) {
		SLOC = sLOC;
	}

	public Double getPuM() {
		return PuM;
	}

	public void setPuM(Double puM) {
		PuM = puM;
	}

	public Double getProM() {
		return ProM;
	}

	public void setProM(Double proM) {
		ProM = proM;
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

}
