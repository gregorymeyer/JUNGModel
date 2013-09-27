package analytics;

import java.util.ArrayList;
import java.util.List;

public class NodeChange {
	
	Boolean isNew = false;
	Boolean isDeleted = false;
	Boolean isClass = false;
	Boolean isPackage = false;
	private Double SLOC;
	private Double PuM;
	private Double ProM;
	private String GMLid = null;
	
	List<String> nodeGroup = new ArrayList<>();
	
	public NodeChange(String gmlid, Double sloc, Double pum, Double prom){
			
			this.GMLid = gmlid;
			this.SLOC = sloc;
			this.PuM = pum;
			this.ProM = prom;
		/*	
			if (SLOC == 0.0 & PuM == 0.0 & ProM==0.0){ isPackage=true; }
			else {isClass = true;}
		*/	
		}
	
	public NodeChange(String gmlid, Double sloc, Double pum, Double prom, Boolean flag){
		
		this.GMLid = gmlid;
		this.SLOC = sloc;
		this.PuM = pum;
		this.ProM = prom;
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
