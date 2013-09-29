package analytics;

public class Metrics {
	
	private Double SLOC;
	private Double PuM;
	private Double ProM;
	
	public Metrics(Double sloc, Double pum, Double prom){
		this.setSLOC(sloc);
		this.setPuM(pum);
		this.setProM(prom);
		
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
	
	

}
