/**
 * This class represent an undirected weighted graph
 * This class implements weighted_graph interface
 * This class can build an undirected graph, add and remove edges and nodes
 * @author Gilad Shotland
 */
package src.ex1.src;

import java.io.Serializable;
import java.util.*;


public class WGraph_DS implements weighted_graph, Serializable  {
    /****************** initialization variables ******************/
    private int edgeSize, MC;
    private HashMap<Integer, node_info> nodes;

    /***************** constructor ********************/
    public WGraph_DS() {
        this.edgeSize = 0;
        this.MC = 0;
        this.nodes = new HashMap<Integer, node_info>();


    }
    /******************  getters ******************/

    /**
     * This method return the Nodeinfo by its id
     *
     * @param key - the node id
     * @return the Nodeinfo by its id, null if none
     */
    @Override
    public node_info getNode(int key) {
        if (!nodes.containsKey(key)) {
            return null;
        }
        return (Nodeinfo) nodes.get(key);
    }
    /**
     * This method return the weight of and edge given by the two keys in the tips of the edge
     *
     * @param node1 the id of node1 (tip1)
     * @param node2 the id of node2 (tip2)
     * @return the weight of the edge. -1 if the edge doesn't exist
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (!hasEdge(node1, node2)) {
            return -1;
        }
        return ((Nodeinfo) nodes.get(node1)).neighbors.get(node2);
    }
    /**
     * This method return a pointer for a collection representing all the nodes in the graph
     *
     * @return Collection<node_data> with all the nodes in the graph
     */
    @Override
    public Collection<node_info> getV() {
        Collection<node_info> values = nodes.values();
        return values;
    }
    /**
     * This method return a Collection containing all the nodes connected to a node given by it's id
     *
     * @param node_id the id of the given node
     * @return Collection<node_info> of the neighbors of the node
     */
    @Override
    public Collection<node_info> getV(int node_id) {// definition - k is the node's degree
        if (!nodes.containsKey(node_id)) {
            return null;
        }
        ArrayList<node_info> ans = new ArrayList<node_info>();
        Collection<Integer> neighbors = ((Nodeinfo) nodes.get(node_id)).neighbors.keySet();
        Iterator iter = neighbors.iterator();
        while (iter.hasNext()) { // k times
            ans.add(nodes.get(iter.next())); //O(1)
        }
        return ans;
        //total complexity O(K)
    }

    /**
     * Thie method return the number of vertices (nodes) in the graph
     *
     * @return the number of the nodes in the graph
     */
    @Override
    public int nodeSize() {
        return nodes.size();
    }

    /**
     * This method return the number of the edges in the graph
     *
     * @return number of edges in the graph
     */
    @Override
    public int edgeSize() {
        return edgeSize;
    }

    /**
     * This method return the Mode Count of the graph.
     * Any change in the inner state of the graph causes an increment in the Mode Count
     *
     * @return ModeCount of the graph
     */
    @Override
    public int getMC() {
        return MC;
    }


    /************** class methods ****************/

    /**
     * This method checks if there is an edge between node 1 and node 2
     *
     * @param node1 the id of node1
     * @param node2 the id of node2
     * @return true if there is an edge between the nodes, false if not
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (nodes.containsKey(node1)) {
            Nodeinfo node = (Nodeinfo) nodes.get(node1);
            return node.neighbors.containsKey(node2);
        }
        return false;

    }



    /**
     * This mehtod add a new node to the graph (with a given key)
     * if there is already a node with such key, the method doesn't do anything
     *
     * @param key the new node's id
     */
    @Override
    public void addNode(int key) {
        if (nodes.containsKey(key)) {
            return;
        }
        nodes.put(key, new Nodeinfo(key));
        MC++;

    }

    /**
     * This method connect and edge between node1 and node2(given by their id's), with an edge with weight >= 0
     * If the edge already exists, the method updates the weight
     *
     * @param node1 the id of node1
     * @param node2 the id of node2
     * @param w     the weight of the edge
     */
    @Override
    public void connect(int node1, int node2, double w) {
        //can we assume that the weight the function gets is valid?
        if (!nodes.containsKey(node1) || !nodes.containsKey(node2))
            return;
        else {
            if (node1 == node2) {
                return;
            }
            if(!hasEdge(node1,node2)){edgeSize++;}
            updateWeight(node1, node2, w); //in this implementation update weight and connect is the same.
        }
        MC++;

    }



    /**
     * This method delete the node (given by it's id) from the graph
     * The method deletes all the edges which start or end at this node
     *
     * @param key the id of the node
     * @return node_info the node that was deleted
     */
    @Override
    public node_info removeNode(int key) {// definition - k is the node's degree
        if (!nodes.containsKey(key)) {
            return null;
        }
        Nodeinfo node = (Nodeinfo) getNode(key);
        for (int neighborkey : node.neighbors.keySet()) { //k neighbors
            ((Nodeinfo) nodes.get(neighborkey)).neighbors.remove(key);//O(1)
            MC++;
            edgeSize--;
        }
        node_info ans = nodes.get(key);
        nodes.remove(key);//O(1)
        MC++;
        return ans;
    }

    /**
     * Delete edge from the graph, given by the two nodes which the edge connect id.
     *
     * @param node1 the id of node1 (tip 1)
     * @param node2 the id of node2 (tip 2)
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (!nodes.containsKey(node1) || !nodes.containsKey(node2)) {
            return;
        }
        Nodeinfo vertex1 = (Nodeinfo) getNode(node1);
        Nodeinfo vertex2 = (Nodeinfo) getNode(node2);
        vertex1.neighbors.remove(node2);
        vertex2.neighbors.remove(node1);
        edgeSize--;
        MC++;
    }


    /**
     * This private method update (and create) the weight of an edge between two nodes(given by their ids)
     *
     * @param node1  id of the first node
     * @param node2  id of the second node
     * @param weight the weight of the edge
     */
    private void updateWeight(int node1, int node2, double weight) {
        ((Nodeinfo) nodes.get(node1)).neighbors.put(node2, weight);
        ((Nodeinfo) nodes.get(node2)).neighbors.put(node1, weight);
    }

    /**
     * method to check if a graph equals to another graph - useful for testing
     * the method checks if the nodes and edges are the same in both of the graphs
     * @param o second graph
     * @return true if and only if the graphs are identical
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WGraph_DS)) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return edgeSize == wGraph_ds.edgeSize &&
                nodes.equals(wGraph_ds.nodes);
    }



    private class Nodeinfo implements node_info, Serializable {
        /****************** initialization variables ******************/
        int key;
        double tag;
        String info;
        HashMap<Integer, Double> neighbors;

        /**
         * Constructor
         *
         * @param key is the id of the new node
         */
        Nodeinfo(int key) {
            this.tag = 0;
            this.key = key;
            this.neighbors = new HashMap<Integer, Double>();

        }

        /**
         * Copy Constructor
         *
         * @param node is the node the Constructor going to deep copy
         */
        Nodeinfo(node_info node) {
            this.tag = node.getTag();
            this.info = node.getInfo();
            this.key = node.getKey();

        }

        /**
         * This method return the id (key) of this node
         *
         * @return the id of the node
         */
        @Override
        public int getKey() {
            return key;
        }

        /**
         * This method return the meta data associated with this node
         *
         * @return
         */
        @Override
        public String getInfo() {
            return info;
        }

        /**
         * This method allow changing the meta data associated with this node
         *
         * @param s the new meta data for this node
         */
        @Override
        public void setInfo(String s) {
            this.info = s;

        }

        /**
         * This method return the temporal data (aka distance, color etc) that used in algorithms on the graph
         *
         * @return the temporal data of this node
         */
        @Override
        public double getTag() {
            return tag;
        }

        /**
         * This method allow setting the tag value for temporal marking a node - useful for algorithms on the graph
         *
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            this.tag = t;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Nodeinfo)) return false;
            Nodeinfo nodeinfo = (Nodeinfo) o;
            return getKey() == nodeinfo.getKey() &&
                    Double.compare(nodeinfo.getTag(), getTag()) == 0 &&
                    Objects.equals(getInfo(), nodeinfo.getInfo()) &&
                    Objects.equals(neighbors, nodeinfo.neighbors);
        }

        @Override
        public int hashCode() {
            return Objects.hash(getKey(), getTag(), getInfo(), neighbors);
        }
    }


}
