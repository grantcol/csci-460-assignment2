import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;


public class Searcher {

	public LinkedList<Node> queue; //acts as a queue for bfs and stack for dfs
	public LinkedList<Node> solutionStack; //holds solution values
	private Comparator<Node> h1Comparator = new NodeH1Comparator();
	private Comparator<Node> h2Comparator = new NodeH2Comparator();
	private Comparator<Node> astarH1Comparator = new NodeH1AstarComparator();
	private Comparator<Node> astarH2Comparator = new NodeH2AstarComparator();
	private PriorityQueue<Node> greedyH1Sort = new PriorityQueue<Node>(10, h1Comparator);
	private PriorityQueue<Node> greedyH2Sort = new PriorityQueue<Node>(10, h2Comparator);
	private PriorityQueue<Node> astarH1Sort = new PriorityQueue<Node>(10, astarH1Comparator);
	private PriorityQueue<Node> astarH2Sort = new PriorityQueue<Node>(10, astarH2Comparator);

	public Searcher(){
		queue = new LinkedList<Node>();
		solutionStack = new LinkedList<Node>();
	}

	//QUEUE FUNCTIONS

	public void queueRoot(Node node) {
		queue.add(node);
	}

	public void queueGreedy(String heur, List<Node> nodes) {
		if(!nodes.isEmpty() && unvisited(nodes)) {
			if(heur.equals("h1")) {
				for(Node n : nodes) {
					if(n.visited == false)
						queue.add(n);
					
				}
				reorderGreedy("h1");
				/*for(Node n : nodes) {
					greedyH1Sort.offer(n);
					System.out.println("node: "+n.name);
				}
				while(!greedyH1Sort.isEmpty()){
					Node temp = greedyH1Sort.poll();
					if(temp.visited == false)
						queue.addFirst(temp);
				}*/
			}
			else if(heur.equals("h2")) {
				for(Node n : nodes) {
					if(n.visited == false)
						queue.add(n);
				}
				reorderGreedy("h2");
				/*for(Node n : nodes) {
					greedyH2Sort.offer(n);
					System.out.println("node: "+n.name);
				}
				while(!greedyH2Sort.isEmpty()){
					Node temp = greedyH2Sort.poll();
					System.out.println("node: "+temp.name+" "+temp.h2);
					if(temp.visited == false)
						queue.addFirst(temp);
				}
				 */		
			}
		}
		else {
			this.queue.pop();
		}
	}

	public void queueAStar(String heur, Node parent, List<Node> nodes) {
		System.out.println(parent.name);
		if(!nodes.isEmpty() && unvisited(nodes)) {
			if(heur.equals("h1")) {
				for(Node n : nodes) {
					if(!n.visited) {
						n.setCost(parent.cost+parent.costs.get(n.name));
						System.out.println(n.name+" "+n.cost);
						if(!queueContains(n.name) && !stackContains(n.name))
							queue.add(n);
					}
				}
				reorderAstar("h1");
			}
			else if(heur.equals("h2")) {
				for(Node n : nodes) {
					if(!n.visited) {
						n.setCost(parent.cost+parent.costs.get(n.name));
						System.out.println(n.name+" "+n.cost);
						if(!queueContains(n.name) && !stackContains(n.name))
							queue.add(n);
					}
				}
				reorderAstar("h2");
			}
		}
		else {
			this.queue.pop();
		}
	}

	public void queueBFS(List<Node> nodes) {
		if(nodes.size() > 0 && unvisited(nodes)) {
			for(int i = nodes.size()-1; i >= 0; i--) {
				Node n = nodes.get(i);
				if(!n.visited) {
					queue.addLast(n);
				}
			}
			this.queue.removeFirst();
		}
		else {
			this.queue.removeFirst();
		}
	}

	public void queueDFS(List<Node> nodes) {
		if(nodes.size() > 0  && unvisited(nodes)) {
			for(Node n : nodes) {
				if(!n.visited)
					queue.addFirst(n);
			}
		} 
		else {
			this.queue.pop();
		}
	}

	public void queueUCS(Node parent, List<Node> nodes) {
		if(nodes.size() > 0 && unvisited(nodes)) {
			for(int i = nodes.size()-1; i >= 0; i--) {
				Node n = nodes.get(i);
				if(!n.visited) {
					n.setCost(parent.cost+parent.costs.get(n.name));
					System.out.println(n.name+" "+n.cost);
					if(!queueContains(n.name) && !stackContains(n.name))
						queue.add(n);
				}
			}
			reorder();
		}
		else {
			this.queue.pop();
		}
	}

	public boolean unvisited(List<Node> nodes) {
		for(Node nn : nodes) {
			if(!nn.visited) {
				return true;
			}
		}
		return false;
	}

	// UTILITIES

	public int size() {
		return queue.size();
	}

	public boolean queueContains(String nodeName) {
		for(Node n : queue) {
			if(n.name.equals(nodeName)) {
				return true;
			}
		}
		return false;
	}

	public boolean stackContains(String nodeName) {
		for(Node n : solutionStack) {
			if(n.name.equals(nodeName)) {
				return true;
			}
		}
		return false;
	}

	public boolean empty() {
		return queue.isEmpty();
	}

	public Node removeTop() {
		return queue.removeFirst();
	}

	public Node removeBack() {
		return queue.removeLast();
	}

	public void pushStack(Node node) {
		if(!stackContains(node.name))
			solutionStack.push(node);
	}

	public Node popStack(Node node) {
		return solutionStack.pop();
	}

	public Node getTop() {
		return queue.getFirst();
	}

	public Node getBack() {
		return queue.getLast();
	}

	public void addOrdered(Node n) {
		if(!queueContains(n.name)) {
			System.out.println("ADDED "+n.name+" To the priority queue");
			queue.addFirst(n);
		}
		else {
			System.out.println("UPDATING : "+n.name);
			update(n);
		}
	}

	public void update(Node n) {
		for(Node nn : queue) {
			if(nn.name.equals(n.name)) {
				nn.setCost(n.cost);
			}
		}
	}

	public void reorder() {
		System.out.println("REORDERING");
		Comparator<Node> comparator = new NodeCostComparator();
		PriorityQueue<Node> costOrdered = new PriorityQueue<Node>(10, comparator);
		LinkedList<Node> orderedSearchQueue = new LinkedList<Node>();
		for(Node n : this.queue) {
			costOrdered.offer(n);
		}
		while(!costOrdered.isEmpty()){
			Node possible = costOrdered.poll();
			if(!stackContains(possible.name))
				orderedSearchQueue.push(possible);
		}
		queue = orderedSearchQueue;
		for(Node nn : queue) {
			System.out.println(nn.name+" "+nn.cost);
		}
	}
	public void reorderGreedy(String heur) {
		LinkedList<Node> orderedSearchQueue = new LinkedList<Node>();
		System.out.println("REORDERING GREEDILY");
		if(heur.equals("h1")) {
			for(Node n : this.queue) {
				greedyH1Sort.offer(n);
			}
			while(!greedyH1Sort.isEmpty()) {
				Node temp = greedyH1Sort.poll();
				if(!stackContains(temp.name))
					orderedSearchQueue.push(temp);
			}
		}
		else if(heur.equals("h2")) {
			for(Node n : this.queue) {
				greedyH2Sort.offer(n);
			}			
			while(!greedyH2Sort.isEmpty()) {
				Node temp = astarH2Sort.poll();
				orderedSearchQueue.add(temp);
			}
		}
		for(Node n : orderedSearchQueue) {
			System.out.println("Node in queue: "+n.name+" H(x) = "+(n.cost+n.h1));
		}
		System.out.println("-------------------");
		this.queue = orderedSearchQueue;
	}
	public void reorderAstar(String heur) {
		LinkedList<Node> orderedSearchQueue = new LinkedList<Node>();
		System.out.println("REORDERING VIA ASTAR");
		if(heur.equals("h1")) {
			for(Node n : this.queue) {
				astarH1Sort.offer(n);
			}
			while(!astarH1Sort.isEmpty()) {
				Node temp = astarH1Sort.poll();
				if(!stackContains(temp.name))
					orderedSearchQueue.push(temp);
			}
		}
		else if(heur.equals("h2")) {
			for(Node n : this.queue) {
				astarH2Sort.offer(n);
			}			
			while(!astarH2Sort.isEmpty()) {
				Node temp = astarH2Sort.poll();
				orderedSearchQueue.add(temp);
			}
		}
		for(Node n : orderedSearchQueue) {
			System.out.println("Node in queue: "+n.name+" H(x) = "+(n.cost+n.h1));
		}
		System.out.println("-------------------");
		this.queue = orderedSearchQueue;
	}
	public class NodeCostComparator implements Comparator<Node> {
		@Override
		public int compare(Node o1, Node o2) {
			// TODO Auto-generated method stub
			if(o1.cost > o2.cost) {
				return -1;
			}
			if(o1.cost < o2.cost) {
				return 1;
			}
			return 0;
		}
	}
	public class NodeH1AstarComparator implements Comparator<Node> {
		@Override
		public int compare(Node o1, Node o2) {
			// TODO Auto-generated method stub
			if((o1.cost + o1.h1) > (o2.cost + o2.h1)) {
				return -1;
			}
			if((o1.cost + o1.h1) < (o2.cost + o1.h1)) {
				return 1;
			}
			return 0;
		}
	}
	public class NodeH2AstarComparator implements Comparator<Node> {
		@Override
		public int compare(Node o1, Node o2) {
			// TODO Auto-generated method stub
			if((o1.cost + o1.h2) > (o2.cost + o2.h2)) {
				return -1;
			}
			if((o1.cost + o1.h2) < (o2.cost + o1.h2)) {
				return 1;
			}
			return 0;
		}
	}
	public class NodeH1Comparator implements Comparator<Node> {
		@Override
		public int compare(Node o1, Node o2) {
			// TODO Auto-generated method stub
			if(o1.h1 > o2.h1) {
				return -1;
			}
			if(o1.h1 < o2.h1) {
				return 1;
			}
			return 0;
		}
	}
	public class NodeH2Comparator implements Comparator<Node> {
		@Override
		public int compare(Node o1, Node o2) {
			// TODO Auto-generated method stub
			if(o1.h2 > o2.h2) {
				return -1;
			}
			if(o1.h2 < o2.h2) {
				return 1;
			}
			return 0;
		}
	}
}
