package graphML;

import java.util.Map;
import org.apache.commons.collections15.Factory;

public class EdgeFactory implements Factory<Edge>
{
    private int n = 0;
    public Edge create()
    {
        return (new Edge(n++,null, null, null));
    }
    
    public Edge create(String source, String target)
    {
        return (new Edge(n++, source, target, null));
    }
    
    public Edge create( String source, String target, Map<String, String> v)
    {
        return (new Edge(n++, source, target, v ));
    }
    
    public Edge create(Boolean d, String source, String target, Map<String, String> v)
    {
        return (new Edge(n++, d, source, target, v ));
    }

};