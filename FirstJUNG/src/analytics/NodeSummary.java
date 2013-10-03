package analytics;

import java.util.ArrayList;
import java.util.List;

public class NodeSummary {

	private final String GMLid;
	private int firstAppearance = 0;
	private Integer lastAppearance = null;
	private List<Integer> changeVersionsList = new ArrayList<>();

	public NodeSummary(String gmLid, int firstAppear) {
		this.GMLid = gmLid;
		this.firstAppearance = firstAppear;
	}

	public String getGMLid() {
		return GMLid;
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

}
