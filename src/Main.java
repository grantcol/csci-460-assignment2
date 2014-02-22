import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;


public class Main {
	
	static SearchGraph sg;
	static GraphConstructor gc = new GraphConstructor("city-edges.txt");
	static String ROOTID = "Alexandria";
	static String GOALID = "Luxor";
	String searchMode;
	private static Scanner userInput;

	public static void main(String [] args) throws IOException {
		sg = gc.generateGraph();
		LinkedList<Node> solution = search(sg, ROOTID);
		printSolution(solution);
	}

	public static LinkedList<Node> search(SearchGraph ss, String rootId) {
		
		/*userInput = new Scanner( System.in );
		String searchQuery;
		String heur;
		System.out.print("Select an algorithm to run (greedy || astar): ");
		searchQuery = userInput.next( );
		System.out.println("Select a hueristic (h1 || h2): ");
		heur = userInput.next();
		System.out.println("sq:"+searchQuery+" heur:"+heur);*/
		
		Searcher sq = new Searcher();
		Node root = ss.getNode(rootId);
		root.cost = 0;
		sq.queueRoot(root);
		root.visited = true;
		
		while(!sq.empty()) {
			Node next = sq.queue.getFirst();
			next.visited = true;
			sq.pushStack(next);
			
			if(next.name.equals(GOALID)) {
				sq.pushStack(next);
				break;
			}
			/*if(searchQuery.equals("greedy")) {
				if(heur.equals("h1"))
					sq.queueGreedy("h1", next.expand());
				else if(heur.equals("h2"))
					sq.queueGreedy("h2", next.expand());
			}
			else if(searchQuery.equals("astar")) {
				if(heur.equals("h1"))
					sq.queueAStar("h1", next, next.expand());
				else if(heur.equals("h2"))
					sq.queueAStar("h2", next, next.expand());
			}*/
			sq.queueAStar("h1", next, next.expand());
			//sq.queueAStar("h2", next, next.expand());
			//sq.queueGreedy("h1", next.expand());
			//sq.queueGreedy("h2", next.expand());
			//sq.queueDFS(next.expand());
			//sq.queueBFS(next.expand());
			//sq.queueUCS(next, next.expand());
		}
		return sq.solutionStack;
	}
	
	public static void printSolution(LinkedList<Node> solution) {
		System.out.println("SOLUTION");
		System.out.println("-----------------");
		for(int i = solution.size()-1; i >=0 ; i--) {
			System.out.println(solution.get(i).name+" h1: "+solution.get(i).h1+" h2: "+solution.get(i).h2);
		}
	}
}
