package analytics;

import java.util.ArrayList;
import java.util.List;

public class PackageStructure extends Packing {
	
	List<Packing> children = new ArrayList<>();
	
	public PackageStructure(String name){
		this.name = name;
	}
	
	public Boolean containsName(String name) {
		for (Packing child: children){
			if(child.getName().equals(name))
			{
				return true;
			}
		}
		
		return false;
	}

	public Integer getNameIndex(String string) {
		
		for (int i = 0; i < children.size(); i++){
			if(string.equals(children.get(i).getName()))
			{
				return i;
			}
		}
		return null;
	}

	public List<Packing> getChildren() {
		return this.children;
	}

	public void addPackageToChildren(String name) {
		children.add(new PackageStructure(name));	
	}
	
	public void addClassToChildren(String name) {
		children.add(new ClassStructure(name));	
	}


}
