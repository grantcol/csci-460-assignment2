##Search Algorithms 
The program will run BFS, DFS and UCS algorithms on the given input (for the purposes of the assignment this is the egypt map). The Program reads in and parses the formatted data and builds a SearchGraph filled with nodes. Each node has has a list of successors which it references during expansion. Distances (costs) are recorded with these successors. The actual implementations are simply queueing variations on the general search function included in main. DFS queues at the front, BFS at the back and UCS queues arbitrarily and reorders every time (PQ). The Searcher class holds two linked lists, one which acts as either the queue (BFS), stack (DFS) or priority queue (UCS) for the traversal and the other which acts as the solution list. All utilites for operations on these two structures are included in Searcher as well. 

##Compilation/Run
Just uncomment the correct lines (51-53)
<pre>
	sq.queueDFS(next.expand());
	sq.queueBFS(next.expand());
	sq.queueUCS(next, next.expand());
</pre> 
Then navigate to the root of the folder where the project was unzipped and run 
<pre>
	java Main
</pre>
and the project should compile and run. 

If running the search.jar 
<pre>java -jar search.jar</pre> 
input "BFS", "DFS" or "UCS" inside the general search function. 

The result is a print of all visited nodes in the order in which they were visited.

