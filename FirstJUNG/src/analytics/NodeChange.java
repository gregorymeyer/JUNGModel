package analytics;

import java.util.ArrayList;
import java.util.List;

public class NodeChange {
	
	private String GMLid = null;
	private Double SLOC;
	private Double PuM;
	private Double ProM;
	
	List<String> nodeGroup = new ArrayList<>();
	
	public NodeChange(String gmlid, Double sloc, Double pum, Double prom){
		this.GMLid = gmlid;
		this.SLOC = sloc;
		this.PuM = pum;
		this.ProM = prom;
		//this.nodeGroup = nodes;
	}
	
	public Double getSLOC() {
		return SLOC;
	}

	public void setSLOC(Double sLOC) {
		SLOC = sLOC;
	}

	public Double getPuM() {
		return PuM;
	}

	public void setPuM(Double puM) {
		PuM = puM;
	}

	public Double getProM() {
		return ProM;
	}

	public void setProM(Double proM) {
		ProM = proM;
	}

	public String getGMLid() {
		return GMLid;
	}

	public void setGMLid(String gMLid) {
		GMLid = gMLid;
	}	
	
	

}
