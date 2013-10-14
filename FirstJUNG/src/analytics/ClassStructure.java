package analytics;

import java.util.ArrayList;
import java.util.List;

public class ClassStructure extends Packing {
	

	private List<Double> versionProbList = new ArrayList<>();
	
	public ClassStructure(String name, List<Double> versProbs)
	{
		this.name = name;
		this.versionProbList = versProbs;
	}
	
	@Override
	public Boolean containsName(String string) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer getNameIndex(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Packing> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPackageToChildren(String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addClassToChildren(String string, List<Double> versProbs) {
		// TODO Auto-generated method stub
		
	}

}
