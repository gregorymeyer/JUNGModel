package graphML;

import java.util.*;


public class GraphContext {
	
		private GraphWrapper graph;
        private HashMap params = new HashMap();
        
        public GraphContext(){
                graph = new GraphWrapper();
        }
        public GraphContext(GraphWrapper g)
        {graph = g;}
        
        public GraphWrapper getGraph(){
                return graph;
        }
        public HashMap getParams(){
                return params;
        }
   
        
};
