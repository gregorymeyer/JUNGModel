package analytics;

import java.util.ArrayList;
import java.util.List;

public class NodeSummary {

	private String GMLid;
	private String nodeType;
	private int firstAppearance = 0;
	private Integer lastAppearance = null;
	private List<Integer> changeVersionsList = new ArrayList<>();
	private List<Double> versionProbList;
	private final Double initProb = 1.0;
	private String packageName;
	private String className;
	private List<Double> versionDeltaSLOCList = new ArrayList<>();

	/**
	 * Constructor for a NodeSummary
	 * 
	 * @param gmLid name of NodeSummary
	 * @param nodeType either a package or a class
	 * @param firstAppear the version of first appearance
	 * @param totalVersions the total number of versions in the project
	 */
	public NodeSummary(String gmLid, String nodeType, int firstAppear, int totalVersions) 
	{
		this.GMLid = gmLid;
		this.firstAppearance = firstAppear;
		this.nodeType = nodeType;
		initVersionProbList(totalVersions);
		initVersionDeltaSLOCList(totalVersions);
	}

	private void initVersionDeltaSLOCList(int totalVersions) 
	{
		//Initialise all deltas to 0.0
		for(int i = 0; i <totalVersions-1; i++)
		{
			this.versionDeltaSLOCList.add(0.0);
		}
	}

	private void initVersionProbList(int totalVersions) 
	{
		this.versionProbList = new ArrayList<Double>(totalVersions);
		for(int i = 0; i<totalVersions; i++)
			versionProbList.add(0.0);
	}
	
	/**
	 * Command the calculate the change probabilities of the node at each version of
	 * the project.
	 */
	public void calculateVersionProbList() 
	{
		// set initial prob to 1
		versionProbList.set(this.firstAppearance, initProb);
		
		Integer endPoint = this.lastAppearance;
		if(this.lastAppearance == null)
			 endPoint = this.versionProbList.size();
		
		if(endPoint > this.firstAppearance)
		{
			if((endPoint - this.firstAppearance) == 1)
			{
				if(this.changeVersionsList.contains(endPoint))
					this.versionProbList.set(endPoint,1.0);
			}
			
			// Create probs for all versions
			int changeCounter = 0;
			for(int i = this.firstAppearance+1; i<= endPoint; i++)
			{
				if(changeVersionsList.contains(i))
				{
					changeCounter += 1;
					versionProbList.set(i, calculateProb(changeCounter,i));
				}
				else if(changeCounter == 0)
				{
					Double prob = (1.0)/((double)(i - this.firstAppearance)); 
					versionProbList.set(i, prob);
				}
				else versionProbList.set(i, calculateProb(changeCounter,i));
			}
		}
		
	}

	private Double calculateProb(int changeCounter,int currentVersion) 
	{
		Double prob = (double)changeCounter/((double)(currentVersion - this.firstAppearance + 1));
		return prob;
	}

	/**
	 * Returns the GMLid of the NodeSummary
	 * 
	 * @return GMLid of NodeSummary
	 */
	public String getGMLid() {
		return GMLid;
	}
	
	/**
	 * Returns the type of node that the NodeSummary represents
	 * 
	 * @return either a 'CLASSNODE' or a 'PACKAGENODE'
	 */
	public String getNodeType()
	{
		return this.nodeType;
	}

	/**
	 * Returns the version at which the node first appeared
	 *  
	 * @return integer version number of first appearance
	 */
	public int getFirstAppearance() {
		// TODO Auto-generated method stub
		return firstAppearance;
	}

	/**
	 * Sets the version at which the node was last seen
	 * 
	 * @param i 
	 */
	public void setLastAppearance(Integer i) {
		this.lastAppearance = i;
	}

	/**
	 * Returns the version at which the node was last seen
	 * 
	 * @return integer number of the version of last appearance
	 */
	public Integer getLastAppearance() {	
		return lastAppearance;
	}

	/**
	 * Returns the number of times that the node changed throughout its lifespan.
	 * 
	 * @return integer number of times node changed
	 */
	public Integer getChangeCount() 
	{
		return this.changeVersionsList.size();
	}

	/**
	 * Add the index of the version at which the node changed.
	 * 
	 * @param i index of version of node change
	 */
	public void addVersionToChangeList(int i) 
	{
		changeVersionsList.add(i);
	}

	/**
	 * Returns the list of versions at which the node changed.
	 * 
	 * @return list of integer version numbers at which the node changed
	 */
	public List<Integer> getChangeVersionsList() {
		return this.changeVersionsList;
	}

	/**
	 * Returns the list of probabilities for the node at version of its lifespan. 
	 * 
	 * @return list of probabilities per version
	 */
	public List<Double> getVersionProbabilities() 
	{
		return this.versionProbList;
	}

	/**
	 * Updates the current NodeSummary's GMLid
	 * 
	 * @param nodeSum object with the GMLid to be assigned to the current NodeSummary
	 */
	public void updateGMLid(NodeSummary nodeSum) 
	{
		this.GMLid = nodeSum.GMLid;
	}

	/**
	 * Assigns the name of the package to which the node belongs
	 * 
	 * @param pakName name of containing package 
	 */
	public void setPackageName(String pakName) 
	{
		this.packageName = pakName;
	}

	/**
	 * Assigns the name of the class
	 * 
	 * @param className name of class
	 */
	public void setClassName(String className) 
	{
		this.className = className;
	}

	/**
	 * Returns the list of changes in lines of code between versions  
	 * 
	 * @return list of changes in lines of code
	 */
	public List<Double> getDeltaSLOCList() 
	{
		return this.versionDeltaSLOCList;
	}

	/**
	 * Assigns the change in the lines of code for a particular version change
	 * 
	 * @param i index of the gap between two versions
	 * @param sloc change in lines of code between two versions
	 */
	public void setVersionDeltaSLOC(int i, Double sloc) 
	{
		// How much the SLOC changed from version i to 1 + 1
		this.versionDeltaSLOCList.set(i,sloc);
		
	}

}
