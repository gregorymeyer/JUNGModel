package analytics;

import java.util.ArrayList;
import java.util.List;

public class NodeChange {
	
	Boolean isNew = false;
	Boolean isDeleted = false;
	Boolean isClass = false;
	Boolean isPackage = false;
/*	
	private Double SLOC;
	private Double PuM;
	private Double ProM;
*/
	
	private String GMLid = null;	
	Metrics metrics;
	List<String> sucNodes = new ArrayList<>();
	List<String> preNodes = new ArrayList<>();
	
	public NodeChange(String gmlid ,Metrics m, List<String> sucs, List<String> pres){
			
			this.GMLid = gmlid;
			this.metrics = m;
			this.sucNodes = sucs;
			this.preNodes = pres;
		/*	
			if (SLOC == 0.0 & PuM == 0.0 & ProM==0.0){ isPackage=true; }
			else {isClass = true;}
		*/	
		}
	
	public NodeChange(String gmlid, Metrics m, List<String> sucs, List<String> pres, Boolean flag){
		
		this.GMLid = gmlid;
		this.metrics = m;
		this.sucNodes = sucs;
		this.preNodes = pres;
		/*
		if (SLOC == 0.0 & PuM == 0 & ProM==0){ isPackage=true; }
		else {isClass = true;}
		*/	
		if (flag) {isNew = true;}
		else if (!flag) {isDeleted = true;}
		
	}

	
	public Boolean isNew(){
		return isNew;
	}
	
	public void setIsNew(Boolean flag){
		isNew = flag;
	}
	
	public Boolean isDeleted(){
		return isDeleted;
	}
	
	public void setIsDeleted(Boolean flag){
		isDeleted = flag;
	}
	
	public Double getSLOC() {
		return metrics.getSLOC();
	}

	public void setSLOC(Double sLOC) {
		metrics.setSLOC(sLOC);
	}

	public Double getPuM() {
		return metrics.getPuM();
	}

	public void setPuM(Double puM) {
		metrics.setPuM(puM);
	}

	public Double getProM() {
		return metrics.getProM();
	}

	public void setProM(Double proM) {
		metrics.setProM(proM);
	}

	public String getGMLid() {
		return GMLid;
	}

	public void setGMLid(String gMLid) {
		GMLid = gMLid;
	}	
	
	

}
