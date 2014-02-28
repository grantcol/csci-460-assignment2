import java.util.HashMap;
import java.util.Map;


public class SearchGraph {
	Map<String, Node> graph; 
	
	public SearchGraph () {
		graph = new HashMap<String, Node>();
	}
	
	public void addNode(Node n) {
		graph.put(n.name, n);
	}
	
	public Node getNode(String nodeName) {
		return graph.get(nodeName);
	}
}
