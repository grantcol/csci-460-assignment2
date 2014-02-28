import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;


public class Node {

	public String name;
	public int cost;
	public int h1;
	public int h2;
	public boolean visited;
	public List<Node> successors = new ArrayList<Node>();
	public Map<String, Integer> costs = new HashMap<String, Integer>();

	public Node(String name) {
		this.name = name;
		this.visited = false;
	}

	public List<Node> expand() {
		Comparator<Node> comparator = new NodeNameComparator();
		PriorityQueue<Node> neighbors = new PriorityQueue<Node>(10, comparator);
		List<Node> alphabeticSuccessors = new ArrayList<Node>();
		if(successors.size() > 0) {
			for(Node nn : successors) {
				neighbors.offer(nn); 
			}
			while(!neighbors.isEmpty()) {
				Node alpha = neighbors.poll();
				alphabeticSuccessors.add(alpha);
			}
			return alphabeticSuccessors;
		}
		return null;
	}

	public boolean successorExists(Node n) {
		for(Node nn : successors) {
			if(nn.name.equals(n.name)) {
				return true;
			}
		}
		return false;
	}

	public void addSuccessor(Node n, int cost) {
		if(!successorExists(n)) {
			successors.add(n);
			costs.put(n.name, cost);
		}
	}
	public void setH1(int h1) {
		this.h1 = h1;
	}
	
	public void setH2(int h2) {
		this.h2 = h2;
	}
	
	public int getH1(){
		return h1;
	}
	
	public int getH2(){
		return h2;
	}
	
	public int getCost(Node n) {
		return costs.get(n.name);
	}
	
	public void setCost(int c) {
		this.cost = c;
	}
	
	public int numSuccessors() {
		return successors.size();
	}

	public void print() {
		System.out.println("NAME: "+this.name);
		for(Node n : successors) {
			System.out.print(n.name+ ", ");
		}
		System.out.println(" total: "+numSuccessors());
	}

	public void printSuccessors(){
		for(Node n : successors){
			System.out.print(n.name+" ");
		}
	}

	public class NodeNameComparator implements Comparator<Node> {
		@Override
		public int compare(Node o1, Node o2) {
			// TODO Auto-generated method stub
			if(o1.name.compareTo(o2.name) < 0) {
				return 1;
			}
			if(o1.name.compareTo(o2.name) > 0 ) {
				return -1;
			}
			return 0;
		}
	}
}
