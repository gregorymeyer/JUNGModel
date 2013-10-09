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
	

	public String getSourceGMLid() 
	{
		return this.source;
	}

	public String getTargetGMLid() 
	{
		return this.target;
	}

	public Integer getSourceChangeCount() {
		return this.sourceChangeList.size();
	}

	public int getFirstAppearance() {	
		return this.firstAppearance;
	}

	public void setLastAppearance(Integer i) {
		this.lastAppearance = i;
	}
	
	public Integer getLastAppearance() {
		return lastAppearance;
	}

	public Integer getSourceAndTargetChangeCount() {
		return this.sourceAndTargetChangeList.size();
	}

	public void addVersionToTargetChanges(int i) 
	{
		targetChangeList.add(i);
	}

	public void addVersionToSourceAndTargetChanges(int i) {
		sourceAndTargetChangeList.add(i);
	}

	public Double getChangePropagationProb() {
		return((double)(sourceAndTargetChangeList.size())/(double)(sourceChangeList.size()));
	}

	public List<Integer> getSourceChangeList() {
		return this.sourceChangeList;
	}

	public List<Integer> getSourceAndTargetChangeList() {
		return this.sourceAndTargetChangeList;
	}

	public void updateSorTar(EdgeSummary edgeSum) 
	{
		// Update source and target
		this.source = edgeSum.source;
		this.target = edgeSum.target;
	}
	
	public int getVersionsSize(){
		return this.versionProbList.size();
	}

}
