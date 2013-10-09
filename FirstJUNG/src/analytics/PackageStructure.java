package analytics;

import java.util.ArrayList;
import java.util.List;

public class PackageStructure {
	
	private String name;
	List<PackageStructure> children = new ArrayList<>();
	
	public PackageStructure(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Boolean containsName(String name) {
		for (PackageStructure child: children){
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

	public List<PackageStructure> getChildren() {
		return this.children;
	}

	public void addToChildren(String name) {
		children.add(new PackageStructure(name));	
	}

}
