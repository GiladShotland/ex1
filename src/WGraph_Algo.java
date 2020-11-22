package src.ex1.src;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms, Serializable  {

    private weighted_graph g;

    /**
     * Init the graph on which this set of algorithms operates on
     *
     * @param g the graph which this set of algorithms operates on
     */
    @Override
    public void init(weighted_graph g) {
        this.g = g;
    }

    /**
     * This method return the underlying graph of which this class work on
     *
     * @return the graph this class work on
     */
    @Override
    public weighted_graph getGraph() {
        return g;
    }

    /**
     * This method compute a deep copy of this weighted graph
     *
     * @return deep copy of this weighted graph
     */
    @Override
    public weighted_graph copy() {
        if (g == null) {
            return null;
        }
        weighted_graph copy = new WGraph_DS();
        for (node_info node : g.getV()) {
            //copying this node

            copy.addNode(node.getKey());
            copy.getNode(node.getKey()).setTag(node.getTag());
            copy.getNode(node.getKey()).setInfo(node.getInfo());
        }
        for (node_info node : g.getV()) {
            //copying the edges connected to this node
            for (node_info neighbor : g.getV(node.getKey())) {
                copy.connect(node.getKey(), neighbor.getKey(), g.getEdge(node.getKey(), neighbor.getKey()));
            }
            if (copy.edgeSize() == g.edgeSize()) {
                break;
            }
        }
        return copy;

    }

    /**
     * This method check if there is a valid path from every node to every other node
     *
     * @return true if there is a valid path from every node to every other node, false if not
     */
    @Override
    public boolean isConnected() {
        if (g == null || g.nodeSize() == 0) {
            return true;
        }
        node_info node = g.getV().iterator().next();
        //if the graph is connected there are n-1 fathers in the graph (n is the number of nodes in the graph)
        //bfs algorithm is less complex the dijkstra's algorithm
        return (bfsAlg(node) >= g.nodeSize() - 1);

    }

    /**
     * This method compute and return the length of the shortest path between two nodes
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return the length of the shortest path between the source node and the destination node
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (this.g == null) {
            return -1;
        }
        if (g.getNode(src) != null && g.getNode(dest) != null) {
            //apply dijkstra's algorithm - that will mark all the nodes with the correct distance from the source node
            dijkstrasAlg(g.getNode(src));
            node_info node = g.getNode(dest);
            if (src == dest) {
                return -1;
            }
            if (node.getTag() == Double.MAX_VALUE) {
                //the algorithm didn't find a valid path to the node
                return -1;
            }
            //there is a valid path to the node, return the minimal path
            return node.getTag();
        }
        //at least one of the nodes doesn't exist
        return -1;
    }

    /**
     * This method Compute and return the shortest path between two nodes as an ordered list
     * the list is built in this way : src --> node1 -->node2...-->dest
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return List<node_info> of the shortest path between source node and destination node
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if (this.g == null) {
            return null;
        }
        if (g.getNode(src) != null && g.getNode(dest) != null) {
            //this implementation of dijkstra's algorithm return a HashMap with all the different fathers in the graph
            HashMap<Integer, node_info> predecessor = dijkstrasAlg(g.getNode(src));
            //the method starts from the destination node, so we need to reverse the path we're getting - stack is useful for that
            Stack<node_info> temp = new Stack();
            node_info node = g.getNode(dest);
            temp.add(node);
            while ( node.getKey() != src) {
                //go to the father of this node
                node = predecessor.get(node.getKey());
                if(node == null){return null;}
                temp.add(node);
            }
            List<node_info> ans = new LinkedList<node_info>();
            while (!temp.isEmpty()) {
                ans.add(temp.pop());
            }
            return ans;

        }
        return null;
    }

    /**
     * This method save this weighted graph to a given file name
     *
     * @param file - the file name (may include a relative path).
     * @return true if the file was successfully saved, false if not
     */
    @Override
    public boolean save(String file) {
        try {
            FileOutputStream myFile = new FileOutputStream(file);
            ObjectOutput oos = new ObjectOutputStream(myFile);
            oos.writeObject(getGraph());
            oos.close();
            return true;

        } catch (Exception e) {

            return false;
        }
    }

    /**
     * This method load a graph to this graph algorithm
     * if the file was successfully loaded the underlying graph
     * of this class will be changed.
     *
     * @param file - file name
     * @return true - if the graph was successfully loaded, false if not
     */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream myFile = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(myFile);
            this.g = (weighted_graph) ois.readObject();
            ois.close();
            return true;
        } catch (Exception error) {

            return false;
        }
    }

    /**
     * This private method is an implementation of BFS algorithm
     * This method is useful for checking connectivity in a graph
     * This method is less complex then dijkstra's algorithm (O(V+E) < O(ElogV))
     *
     * @param src source node which the algorithm starts from
     * @return a HashMap with the predecessors in the graph.
     */
    private int bfsAlg(node_info src) {
        if (this.g == null) {
            return -1;
        }
        HashMap<Integer, node_info> fathers = new HashMap<Integer, node_info>();
        Queue<node_info> q = new ArrayDeque<node_info>();
        for (node_info node : g.getV()) {
            node.setTag(-1);//set all nodes to -1 distance(for checking connectivity)

        }
        int counter = 0;

        src.setTag(0);//source node - distance : 0

        q.add(src);
        //starting traversal
        while (!q.isEmpty()) {

            node_info current = q.poll();
            for (node_info son : g.getV(current.getKey())) {
                if (son.getTag() == -1) {
                    q.add(son);
                    son.setTag(1);//tag this node distance from the source
                    counter++;

                }
            }
        }
        return counter;


    }


    /**
     * This private method is an implementation of Dijkstra's Algorithm
     * The method finds the shortest path from the source node given by it's id to every node in the graph(if the path exist)
     * In this implementation the method returns a HashMap with all the predecessors of the nodes that have a valid path between them and the source node.
     *
     * @param src the id of the source node
     * @return Hashmap of the predecessors
     */
    private HashMap<Integer, node_info> dijkstrasAlg(node_info src) {

        HashMap<Integer, Boolean> visited = new HashMap<Integer, Boolean>();
        for (node_info node : g.getV()) {
            visited.put(node.getKey(), false);
            node.setTag(Double.MAX_VALUE);
        }
        src.setTag(0);

        HashMap<Integer, node_info> predecessors = new HashMap<Integer, node_info>();
        PriorityQueue<node_info> queue = new PriorityQueue<node_info>(new nodeCompare());
        queue.add(src);
        while (!queue.isEmpty()) {
            node_info temp = queue.poll();
            if (!visited.get(temp.getKey())) {
                for (node_info neighbor : g.getV(temp.getKey())) {

                    double dist = temp.getTag() + g.getEdge(temp.getKey(), neighbor.getKey());
                    if (neighbor.getTag() > dist) {
                        neighbor.setTag(dist);
                        predecessors.put(neighbor.getKey(), temp);

                        queue.add(neighbor);
                    }

                }
                visited.put(temp.getKey(), true);
            }

        }
        return predecessors;
    }

    /**
     * Comparator class for node_info class
     * This Comparator uses for comparing based algorithms
     * This Comparator is needed for the Priority Queue comparing in the dijkstra's algorithm
     */
    private class nodeCompare implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            node_info node1 = (node_info) o1;
            node_info node2 = (node_info) o2;
            if (node2.getTag() == node1.getTag()) {
                return 0;
            }
            if (node1.getTag() < node2.getTag()) {
                return -1;
            }
            if (node1.getTag() > node2.getTag()) {
                return 1;
            }
            return 0;
        }
    }
}
