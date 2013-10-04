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
	
	public EdgeSummary(String source, String target, int firstAppear) 
	{
		this.source = source;
		this.target = target;
		this.firstAppearance = firstAppear;

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

	public void updateSorTar(EdgeSummary edgeSum) 
	{
		// Update source and target
		this.source = edgeSum.source;
		this.target = edgeSum.target;
	}

}
