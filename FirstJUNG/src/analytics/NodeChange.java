package analytics;

import java.util.ArrayList;
import java.util.List;

public class NodeChange 
{	
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
	
	/**
	 * Constructor for NodeChange
	 * 
	 * @param gmlid name of NodeChange
	 * @param m metrics object with information pertaining to the particular NodeChange
	 */
	public NodeChange(String gmlid ,Metrics m ){}
	
	/**
	 * Overloaded NodeChange constructor
	 * 
	 * @param gmlid name of NodeChange
	 * @param m metrics object with information pertaining to the particular NodeChange
	 * @param nodeType type of node, either a 'CLASSNODE' or a 'PACKAGENODE'
	 */
	public NodeChange(String gmlid, Metrics m, String nodeType)
	{
		this.GMLid = gmlid;
		this.metrics = m;
		this.nodeType = nodeType;
		this.hasChangedSinceLastGraph = hasNodeChanged();
	}

	/**
	 * Overloaded NodeChange constructor
	 * 
	 * @param gmlid name of NodeChange
	 * @param m metrics object with information pertaining to the particular NodeChange
	 * @param nodeType type of node, either a 'CLASSNODE' or a 'PACKAGENODE'
	 * @param flag boolean flag indicating whether a node changed between two versions
	 */
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
	
	/**
	 * Returns the neighbours in the dependency graph that changed alongside the node
	 * 
	 * @return list of NodeChange objects that changed alongside the NodeChange
	 */
	public List<NodeChange> getChangedNeighbours() 
	{
		return this.neighboursThatChanged;
	}
	
	/**
	 * Returns the status of the node in the dependency network
	 * 
	 * @return true if the node is new in the graph, false otherwise
	 */
	public Boolean isNew(){
		return isNew;
	}
	
	/**
	 * Sets the status of the node in the graph
	 * 
	 * @param flag true if the nod is new, false otherwise
	 */
	public void setIsNew(Boolean flag){
		isNew = flag;
	}
	
	/**
	 * Returns the life status of the node in the graph
	 *  
	 * @return true if the node has been deleted between two graph versions, false otherwise
	 */
	public Boolean isDeleted(){
		return isDeleted;
	}
	
	/**
	 * Sets the life status of the node in the graph
	 * 
	 * @param flag set to true if the node is deleted between two versions, false otherwise
	 */
	public void setIsDeleted(Boolean flag){
		isDeleted = flag;
	}
	
	/**
	 * Returns the difference in the lines of code between two graph versions.
	 *  
	 * @return lines of code difference between two versions
	 */
	public Double getSLOC() {
		return metrics.getSLOC();
	}

	/**
	 * Sets the difference in the lines of code between two graph versions.
	 * 
	 * @param sLOC lines of code difference between two graph versions
	 */
	public void setSLOC(Double sLOC) {
		metrics.setSLOC(sLOC);
	}

	/**
	 * Returns the difference in the public methods between two graph versions   
	 * 
	 * @return the public method number difference between two graph versions
	 */
	public Double getPuM() {
		return metrics.getPuM();
	}

	/**
	 * Sets the difference in the public methods between two graph versions
	 *  
	 * @param puM the public method number difference
	 */
	public void setPuM(Double puM) {
		metrics.setPuM(puM);
	}

	/**
	 * Returns the difference in the number of private methods between two graph versions
	 *   
	 * @return the difference in the number of private methods
	 */
	public Double getProM() {
		return metrics.getProM();
	}

	/**
	 * Sets the difference in the number of private methods between two graph versions
	 * 
	 * @param proM difference in the number of private methods
	 */
	public void setProM(Double proM) {
		metrics.setProM(proM);
	}

	/**
	 * Returns the GMLid of the node
	 * 
	 * @return GMLid of the node
	 */
	public String getGMLid() {
		return GMLid;
	}

	/**
	 * Sets the GMLid of the node 
	 * 
	 * @param gMLid of the node
	 */
	public void setGMLid(String gMLid) {
		GMLid = gMLid;
	}
	
	/**
	 * Returns the type of node
	 * 
	 * @return either 'CLASSNODE' or 'PACKAGNODE'
	 */
	public String getNodeType()
	{
		return this.nodeType;
	}

	/**
	 * Returns the changes status of the node between two graph versions
	 *  
	 * @return true if the node changed, false otherwise
	 */
	public boolean hasChanged() 
	{
		return this.hasChangedSinceLastGraph;
	}

	/**
	 * Assigns a list of neighbouring nodes in the graph that changed alongside the present node  
	 * 
	 * @param changedNeighbours list of NodeChanges that changed alongside the present node 
	 */
	public void setChangedNeighbours(List<NodeChange> changedNeighbours) 
	{
		this.neighboursThatChanged = changedNeighbours;	
	}

	/**
	 * Sets the list of successor nodes the present node
	 * 
	 * @param successors list of successor NodeChanges to the present node
	 */
	public void setSuccessors(List<NodeChange> successors) 
	{
		this.successors = successors;	
	}

	/**
	 * Returns the list of successor NodeChanges to the present NodeChange
	 * 
	 * @return list of successor NodeChanges to the present NodeChange
	 */
	public List<NodeChange> getSuccessors() 
	{
		return this.successors;
	}
	
	/**
	 * Sets the predecessor NodeChanges to the present NodeChange
	 * 
	 * @param predecessors list of predecessor NodeChanges to the present NodeChange
	 */
	public void setPredecessors(List<NodeChange> predecessors) 
	{
		this.predecessors = predecessors;	
	}

	/**
	 * Returns the list of predecessor NodeChanges to the present NodeChange
	 * 
	 * @return list of predecessor NodeChanges to the present NodeChange
	 */
	public List<NodeChange> getPredecessors() 
	{
		return this.predecessors;
	}
}
