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

	public String getGMLid() {
		return GMLid;
	}
	
	public String getNodeType()
	{
		return this.nodeType;
	}

	public int getFirstAppearance() {
		// TODO Auto-generated method stub
		return firstAppearance;
	}

	public void setLastAppearance(Integer i) {
		this.lastAppearance = i;
	}

	public Integer getLastAppearance() {	
		return lastAppearance;
	}

	public Integer getChangeCount() 
	{
		return this.changeVersionsList.size();
	}

	public void addVersionToChangeList(int i) 
	{
		changeVersionsList.add(i);
	}

	public List<Integer> getChangeVersionsList() {
		return this.changeVersionsList;
	}

	public List<Double> getVersionProbabilities() 
	{
		return this.versionProbList;
	}

	public void updateGMLid(NodeSummary nodeSum) 
	{
		this.GMLid = nodeSum.GMLid;
	}

	public void setPackageName(String pakName) 
	{
		this.packageName = pakName;
	}

	public void setClassName(String className) 
	{
		this.className = className;
	}

	public List<Double> getDeltaSLOCList() 
	{
		return this.versionDeltaSLOCList;
	}

	public void setVersionDeltaSLOC(int i, Double sloc) 
	{
		// How much the SLOC changed from version i to 1 + 1
		this.versionDeltaSLOCList.set(i,sloc);
		
	}

}
