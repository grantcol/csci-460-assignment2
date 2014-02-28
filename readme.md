##Search Algorithms 
The program will run Greedy and A* search algorithms on the given input (for the purposes of the assignment this is the egypt map). The Program reads in and parses the formatted data and builds a SearchGraph filled with nodes. Each node has has a list of successors which it references during expansion. Distances (costs) are recorded with these successors. Each node also has heuristic values for informed search (h1/h2) The actual implementations are simply queueing variations on the general search function included in main. DFS queues at the front, BFS at the back and UCS queues arbitrarily and reorders every time (PQ). The Greedy and A* queueing functions utilize a priority queue to reorder elements acording to thier respective heuristics during traversal. The Searcher class holds two linked lists, one which acts as either the queue (BFS), stack (DFS) or priority queue (UCS) for the traversal and the other which acts as the solution list. All utilites for operations on these two structures are included in Searcher as well. 

##Compilation/Run
Just uncomment the correct lines (59-65)
<pre>
sq.queueGreedy("h1", next.expand());
//sq.queueGreedy("h2", next.expand());
//sq.queueAStar("h1", next, next.expand());
//sq.queueAStar("h2", next, next.expand());
</pre> 
Then navigate to the root of the folder where the project was unzipped and run 
<pre>java Main</pre>
and the project should compile and run. 

If running the search.jar 
<pre>java -jar informed.jar</pre> 
follow the prompts and input "greedy" or "astar" to choose the algoritm. Then input "h1" or "h2" to select a heuristic 

The result is a print of all visited nodes in the order in which they were visited.

##Performance
1. Regarding A* performance under h1 and h2: there is no difference with the given data set. The number of nodes traversed by the A* algorithm is 15 either way. The heuristics were balanced out by the path cost to the node in case of this data set. Take for example Nekhel and Matruh, which had h1 of 174 and 133 and h2 of 189 145, however, the path cost to Nekhel is 245 which is large enough to negate this difference in the A* traversal of both heuristics. This is strictly due to the chosen data points and the performance may differ if h1 and h2 were applied to a different map.

2. The traversals provided by my implementations are in line with my predictions. The greedy searches, judging desireability only on the heuristic and ignoing cost, were faster in this case because the distance from Luxor decreased as the traversal continued. The difference between h1 and h2 is clear because Kharga has a slightly lower h2 value and so it is skipped rather than expanded like it is when traversing by h1. The A* traversal is clear if traced by pen and paper as the path cost + hueristic calculation can get tedious if one is tracing mentally. The result is a bit surprising at first glance since it expands more nodes than the greedy search but it is clear that greedy search wins out simply due to the arrangement of hueristic values. A* is less efficient here because of the way the path costs and the heuristics line up, that is they align so as to force expansion of all nodes but this is not always the case. 