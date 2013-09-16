package graphML;

public class GraphManager {
		
			public GraphManager(){};
			
		    public GraphContext captureGraphMLFile(String file) throws Exception{     
	            
		    	GraphmlIO graphmlIO = new GraphmlIO();
	            if (file!=null){
	            	String fileContent = GraphmlIO.readFile(file);
                    GraphWrapper retGraph = new GraphWrapper(graphmlIO.parse(fileContent));
                    GraphContext ret = new GraphContext(retGraph);
                    return ret;
	            }
	            else{
                    System.out.print("no input provided");
                    return null;
	            }
		            
		    }
};
