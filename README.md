OOP Ex1  - Weighted Graph
created as an assingment in Object Oriented Programming Course while 2nd year at Computer Science degree at Ariel University.
This project include Two Classes - WGraph_DS class, which represnt the graph and include basic methods to operate on, and WGraph_Algo class, that mainly represent more complex 
algorithms that can be operated on the graph.

WGraph_DS : 
This graph represents a weighted graph, and implement the weighted_graph interface and Serializable interface.
The class has a private inner class:
Nodeinfo, that represent a node in the graph, implements node_info interace and Serializable interface. Every node has an id (int - key), tag (double -  useful for  storing temporal data in algorithms), and  Info (String - for storing data)
In addition, every node has also a HashMap that represents the edges connected to the node (the keys of the map are the ids of the nodes in the other tip of the edges, and the values are the weight of the edges),
the HashMap gives the option for doing many operations in O(1) complexity.
Every node is being created throw the graph with a key given by the user. 
The main data structure the graph is based on is HashMap, that stores all the nodes in the graph by their ids, this give fast access to every node by its key. 
Every object in the class has also counters for the numbers of nodes and edges, and a counter for the mode changes in the graph.
This class and create a weighted graph, and operate simple methods as getting a node from a graph (by id), getting a collection of all the nodes in the graph, adding a node, removing a node, connect between nodes, remove edges and getting a neighbors list of a given node.

WGraph_Algo:
This graph conatin algorithms to operate on a weighted graph, and implement the weighted_graph_algo interface
The class has 8 methods:
1. Init - this method init a graph (by getting a pointer to the graph) for starting operate algorithms on it.
2. getGraph - this method return a pointer to the graph that is being operated on by the classe's algorithms 
3. copy - this method return a deep copy of the graph. Note: this method *don't* copy the mode changes counter.
4. isConnected - this method check if a graph is connected( a graph is called 'connected' if there is a valid path between every node in the graph to every other node), if the graph is connected the method return true, if not it returns false. The method uses BFS Algorithm
 The function make a traversal with the BFS algorithm, which make the traversal visit in every node that there is a valid path to. 
 If the traversal didn't visit all the nodes - the function will return false
5. ShortestPathDist - this method compute and return the distance between a source node and a destination node (distance is defined by the total weight of the edges between the source and destination nodes). If there is no valid path between the nodes - the method will return -1. The method use Dijkstra's Algorithm.
 The function make a traversal with the Dijkstra's algorithm method from the source node, every node in the graph get a tag that represent the distance from the node to the source node.
6. ShortestPath - this method compute and return a collection of the shortest path between a source node and a destination node (distance is defined as i mentioned in ShortestPathDist method). If there is no vaid path between the nodes the method will return null. The method uses Dijkstra's algorithm.
This function make a traversal with the Dijkstra's algorithm method from the sourch node, every node in the graph get a tag that represent the distance from the node to tthe source node.
 In this class I implemented Dijkstra's algorithm method with returning a HashMap of parents - enter a node's key and get the node's parent. So to get the path between the nodes the function 
 uses the HashMap, start from the destination, enter it to a stack an than get it's parent, until the function gets the source. After finishing getting the parents the function enter all
 the nodes from the stack to a list, in purpose to get the right order. 
7. Save - this method save the graph which the WGraph_Algo inited on to a file. The method using the Serializable implementation of WGraph_DS class (and NodeInfo class).
8. Load - this method load and init a graph from a file. This method using the Serializable implementation of WGraph_DS class.

*In this project i implmented the Dijkstra's and BFS algorithms, with a little change of returning the HashMap of parents(predecessors) in the graph.
*I implemented both Dijkstra's and BFS algorithms, because Dijstra's Algorithm is needed for traversal and tagging a weighted graph, and for checking connectivity the BFS if better then Dijkstra's Algorithm because of its complexity.
 