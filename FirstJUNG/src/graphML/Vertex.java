package graphML;

import javax.xml.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@XmlRootElement(name="node")
public class Vertex{
           
	private Integer id;
    private Map<String, String> data;
                   
    public Vertex(){}
                
    public Vertex(Integer i){
        this.id=i;
    }
                
    public Vertex( Integer i, Map<String, String> v){
        this.id =i;
        this.data = v;
    }
                  
    public String getProperty(String k){
           return data.get(k);
    }
           
    public Boolean hasProperty(String k){
           return data.containsKey(k);
    }
                      
    @XmlAttribute
    public Integer getId(){
           return id;
    }
           
    public Boolean addData(String k, String v){ 
        try{
                data.put(k, v);
        }catch (Exception e) {
                return false;
        }
        return true;
    }
           
    public boolean Equals(String s){ 
           if (s!=null){
                   for (Map.Entry<String, String> entry : data.entrySet()){
                           if (entry.getValue()==s){
                                   return true;
                           }
                   }       
           	}
           return false;
     }

	public Boolean containsValue(String value) {
		
		return data.containsValue(value);
	}  
     
};
