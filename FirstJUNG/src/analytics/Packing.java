package analytics;

import java.util.List;

public abstract class Packing {
	
	protected String name;
	
	public String getName() {
		return name;
	}

	abstract public Boolean containsName(String string);

	abstract public Integer getNameIndex(String string);

	abstract public List<Packing> getChildren();

	abstract public void addPackageToChildren(String string);

	abstract public void addClassToChildren(String string, List<Double> versProbs);
	
}