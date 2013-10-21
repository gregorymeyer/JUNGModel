package analytics;

import java.util.ArrayList;
import java.util.List;

public class EdgeSummary 
{
	private Integer weight = 1;
	private String source;
	private String target;
	private int firstAppearance;
	private Integer lastAppearance = null;
	private List<Integer> sourceChangeList = new ArrayList<>();
	private List<Integer> sourceAndTargetChangeList = new ArrayList<>();
	private List<Double> versionProbList;
	private final Double initProb = 1.0;
	private List<Integer> targetChangeList = new ArrayList<>();
	
	/**
	 * Constructor for EdgeSummary
	 * 
	 * @param source name of the source node
	 * @param target name of the target node
	 * @param firstAppear version at which the relationship began.
	 * @param totalVersions total number of versions in the project
	 */
	public EdgeSummary(String source, String target, int firstAppear, int totalVersions) 
	{
		this.source = source;
		this.target = target;
		this.firstAppearance = firstAppear;
		initVersionProbList(totalVersions);
	}
	
	private void initVersionProbList(int totalVersions) 
	{
		this.versionProbList = new ArrayList<Double>(totalVersions);
		for(int i = 0; i<totalVersions; i++)
			versionProbList.add(0.0);
	}
	
	/**
	 * Command to calculate the change probability of the EdgeSummary at each version of
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
		{	//Special case
			if((endPoint - this.firstAppearance) == 1)
			{ 
					this.versionProbList.set(endPoint,1.0);
			}
			
			// Create probs for all versions
			else {
				for(int i = this.firstAppearance+1; i<= endPoint; i++)
				{
					versionProbList.set(i, calculateProb(i));
				}
			}
		}
		
	}

	private Double calculateProb(int currentVersion) 
	{
		int targetChangeCount = 0;
		int srcTarChangeCount = 0;
		//Count target changes up until currentVersion
		for(Integer version : targetChangeList){
			if(version <= currentVersion)
				targetChangeCount++;
		}
		//Count srcTar changes up until currentVersion
		for(Integer version : sourceAndTargetChangeList){
			if(version <= currentVersion)
				srcTarChangeCount++;
		}
		if(targetChangeCount == 0){return 0.0;}
		
		return (double)(srcTarChangeCount)/(double)(targetChangeCount);		
	}
	

	/**
	 * Returns the GMLid of the source node
	 * 
	 * @return the GMLid of the source
	 */
	public String getSourceGMLid() 
	{
		return this.source;
	}

	/**
	 * Returns the GMLid of the target node
	 * 
	 * @return the GMLid of the target node 
	 */
	public String getTargetGMLid() 
	{
		return this.target;
	}

	/**
	 * Returns the number of times that the source node has changed throughout it's lifespan
	 * 
	 * @return the integer amount of source changes
	 */
	public Integer getSourceChangeCount() {
		return this.sourceChangeList.size();
	}

	/**
	 * Returns the version at which the EdgeSummay first appeared. That is, the version where
	 * the relationship between the classes began.
	 * 
	 * @return the version number of first appearance 
	 */
	public int getFirstAppearance() {	
		return this.firstAppearance;
	}

	/**
	 * Set the version number where the EdgeSummary was last seen. That is, the version where
	 * the relationship between classes was last seen.
	 * 
	 * @param i version number of last recorded class relationship
	 */
	public void setLastAppearance(Integer i) {
		this.lastAppearance = i;
	}
	
	/**
	 * Returns the version number at which the EdgeSummary was last seen. That is, the last version
	 * where there is record of a relationship between classes. 
	 * 
	 * @return the version where the EdgeSummary was last seen.
	 */
	public Integer getLastAppearance() {
		return lastAppearance;
	}

	/**
	 * Returns the number of times that the source has changed given that the target changed.
	 *  
	 * @return the conditional source change count
	 */
	public Integer getSourceAndTargetChangeCount() {
		return this.sourceAndTargetChangeList.size();
	}

	/**
	 * Adds the index of the version where the target changed.
	 * 
	 * @param i index of version where target changed.
	 */
	public void addVersionToTargetChanges(int i) 
	{
		targetChangeList.add(i);
	}

	/**
	 * Adds the index of a version where both the source and target both changed.
	 * 
	 * @param i index of version where source and target both changed
	 */
	public void addVersionToSourceAndTargetChanges(int i) {
		sourceAndTargetChangeList.add(i);
	}

	/**
	 * Returns the conditional source change probability. That is, the probability that the
	 * source will change given that the target has changed.
	 * 
	 * @return the conditional source change probability
	 */
	public Double getChangePropagationProb() {
		return((double)(sourceAndTargetChangeList.size())/(double)(sourceChangeList.size()));
	}

	/**
	 * Returns the list of versions where the source node changed.
	 * 
	 * @return list of integers representing version numbers of source node changes
	 */
	public List<Integer> getSourceChangeList() {
		return this.sourceChangeList;
	}

	/**
	 * Returns the list of versions where both the source and target changed.
	 * 
	 * @return list of integers representing version numbers of source and target node changes.
	 */
	public List<Integer> getSourceAndTargetChangeList() {
		return this.sourceAndTargetChangeList;
	}

	/**
	 * Updates the names of the source and targets of the EdgeSummary
	 * 
	 * @param edgeSum object whose source and target names are to be assigned to the current object 
	 */
	public void updateSorTar(EdgeSummary edgeSum) 
	{
		// Update source and target
		this.source = edgeSum.source;
		this.target = edgeSum.target;
	}
	
	/**
	 * Returns the number of total versions in the project
	 * 
	 * @return integer number of versions in the project
	 */
	public int getVersionsSize(){
		return this.versionProbList.size();
	}

}
