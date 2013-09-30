package analytics;

public class NodeSummary {

	private final String GMLid;
	private final int firstAppearance;
	private Integer lastAppearance = null;
	private Integer changeCount = 0;

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
		return this.changeCount ;
	}

	public void incrementChangeCount() 
	{
		this.changeCount += 1;
	}

}
