package analytics;

public class EdgeSummary 
{

	private String sourceGMLid;
	private String targetGMLid;
	private int firstAppearance;
	private Integer lastAppearance = null;
	private Integer sourceChangeCount;
	private Integer targetChangeCount;

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
		return this.sourceChangeCount;
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

	public void incrementSourceChangeCount() {
		this.sourceChangeCount +=1;
		
	}

	public void incrementTargetChangeCount() {
		this.targetChangeCount +=1;
		
	}

	public Integer getTargetChangeCount() {
		return this.targetChangeCount;
	}

}
