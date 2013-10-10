package analytics;

import java.util.List;

public class ClassStructure extends Packing {
	
	Integer depth;
	public ClassStructure(String name)
	{
		this.name = name;
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
	public void addClassToChildren(String string) {
		// TODO Auto-generated method stub
		
	}

}
