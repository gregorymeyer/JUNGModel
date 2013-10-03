package analytics;

import java.util.ArrayList;
import java.util.List;

public class EdgeSummary 
{

	private String sourceGMLid;
	private String targetGMLid;
	private int firstAppearance;
	private Integer lastAppearance = null;
	private Integer sourceChangeCount;
	private Integer targetChangeCount;
	private List<Integer> sourceChangeList = new ArrayList<>();
	private List<Integer> sourceAndTargetChangeList = new ArrayList<>();
	
	public EdgeSummary(String source, String target, int firstAppear) 
	{
		this.sourceGMLid = source;
		this.targetGMLid = target;
		this.firstAppearance = firstAppear;
		this.sourceChangeCount = 0;
		this.targetChangeCount = 0;
	}

	public String getSourceGMLid() 
	{
		return this.sourceGMLid;
	}

	public String getTargetGMLid() 
	{
		return this.targetGMLid;
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

	public void addVersionToSourceChanges(int i) {
		sourceChangeList.add(i);
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

}
