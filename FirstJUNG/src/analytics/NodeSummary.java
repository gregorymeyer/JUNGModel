package analytics;

public class NodeSummary {

	private final String GMLid;
	private final int firstAppearance;
	private Integer deletedAt = null;

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

	public void deletedAt(Integer i) {
		this.deletedAt = i;
		
	}

	public Integer getDeletedAt() {	
		return deletedAt;
	}

}
