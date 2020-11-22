package src.ex1.tests;

import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {
    @Test
    void getKey(){
        weighted_graph graph = new WGraph_DS();
        graph.addNode(5);
        node_info node = graph.getNode(5);
        int key = node.getKey();
        assertEquals(5,key);
    }
    @Test
    void getInfo(){
        weighted_graph graph = new WGraph_DS();
        graph.addNode(0);
        node_info node = graph.getNode(0);
        String s = node.getInfo();
        assertEquals(null,s);
        node.setInfo("OOP");
        s = node.getInfo();
        assertEquals("OOP",s);
    }
    @Test
    void setInfo(){
        weighted_graph graph = generateGraph(1);
        graph.addNode(0);
        node_info node = graph.getNode(0);
        node.setInfo("gilad");
        assertEquals("gilad",node.getInfo());
        node.setInfo("shotland");
        assertEquals("shotland",node.getInfo());
        node.setInfo("grade100");
        assertEquals("grade100",node.getInfo());
    }
    @Test
    void getTag(){
        weighted_graph graph = generateGraph(1);
        graph.addNode(0);
        node_info node = graph.getNode(0);
        assertEquals(0,node.getTag());

    }
    @Test
    void setTag(){
        weighted_graph graph = generateGraph(1);
        graph.addNode(0);
        node_info node = graph.getNode(0);
        node.setTag(10.111);
        assertEquals(10.111,node.getTag());
        node.setTag(3.5);
        assertEquals(3.5,node.getTag());
        node.setTag(0);
        assertEquals(0,node.getTag());
        node.setTag(0.0);
        assertEquals(0,node.getTag());
    }

    @Test
    void addNode(){
        weighted_graph graph = new WGraph_DS();
        graph.addNode(1);
        assertNotEquals(null,graph.getNode(1));
    }


    //tests for WGraph_DS
    @Test
    void getNode(){
        weighted_graph graph = generateGraph(1);
        graph.addNode(0);
        node_info node = graph.getNode(0);

        assertEquals(0,node.getKey());
        node_info node1 = graph.getNode(5);
        assertEquals(null,node1);
    }
    @Test
    void removeNode1(){
        weighted_graph graph = new WGraph_DS();
        graph.addNode(3);
        graph.removeNode(3);
        assertEquals(null,graph.getNode(5));
    }

    @Test
    void nodeSize1(){
        weighted_graph graph = generateGraph(15);
        assertEquals(16,graph.nodeSize());
        graph.removeNode(5);
        assertEquals(15,graph.nodeSize());
    }
    @Test
    void edgeSize1(){
        weighted_graph graph = generateGraph(1);
        graph.connect(0,1,1);
        assertEquals(1,graph.edgeSize());
        graph.connect(0,1,2);
        assertEquals(1,graph.edgeSize());
        graph.removeEdge(1,0);
        assertEquals(0,graph.edgeSize());

    }

    @Test
    void getMC1(){
        weighted_graph graph = generateGraph(20);
        assertEquals(21,graph.getMC());
        graph.removeNode(20);
        graph.removeNode(19);
        assertEquals(23,graph.getMC());
        int x = 24;
        for (int i = 0; i < 10 ; i++) {
            graph.removeNode(i);
            assertEquals(x+i,graph.getMC());

        }

    }

    @Test
    void hasEdge(){
        weighted_graph graph = generateGraph(2);
        graph.connect(0,1,3);
        assertTrue(graph.hasEdge(0,1));
        assertTrue(graph.hasEdge(1,0));
        assertFalse(graph.hasEdge(1,3));
        assertFalse(graph.hasEdge(1,2));
        assertFalse(graph.hasEdge(2,1));
        assertFalse(graph.hasEdge(0,2));
        assertFalse(graph.hasEdge(2,0));
        assertFalse(graph.hasEdge(2,2));
        assertFalse(graph.hasEdge(1,1));
        assertFalse(graph.hasEdge(0,0));
        weighted_graph graph1 = generateGraph(1000);
        for (int i = 0; i < 10001 ; i++) {
            for (int j = 0; j < 1001 ; j++) {
                if(i!=j){
                    graph1.connect(i,j,1);
                }
            }
        }
        Random x = new Random();
        Random y = new Random();
        for (int i = 0; i < 100000 ; i++) {
            int a = x.nextInt(1000);
            int b = x.nextInt(1000);
            if(a!=b) {
                assertTrue(graph1.hasEdge(a, b));
            }
            else{
                assertFalse(graph1.hasEdge(a,b));

            }
        }
    }
    @Test
    void getEdge(){
        weighted_graph graph = generateGraph(2);
        graph.connect(0,1,2);
        assertEquals(2,graph.getEdge(0,1));
        assertEquals(2,graph.getEdge(1,0));
        graph.connect(0,1,1.234);
        assertEquals(1.234,graph.getEdge(0,1));
        assertEquals(1.234,graph.getEdge(1,0));
        assertEquals(-1,graph.getEdge(1,2));
        assertEquals(-1,graph.getEdge(2,0));


    }
    @Test
    void getV(){
        weighted_graph graph = generateGraph(20);
        Collection nodes = graph.getV();
        assertEquals(21,nodes.size());
        node_info node1 = graph.getNode(5);
        graph.removeNode(5);
        graph.addNode(50);
        node_info node2 = graph.getNode(50);
        assertFalse(nodes.contains(node1));
        assertTrue(nodes.contains(node2));
        assertEquals(graph.nodeSize(),nodes.size());


    }
    void getV1(){
        weighted_graph graph = generateGraph(30);
        Collection neighbors = graph.getV(11);
        assertEquals(0,neighbors.size());
        for (int i = 0; i < 10; i++) {
            graph.connect(i,11,i+5);
        }
        assertEquals(10,neighbors.size());
        for (int i = 0; i < 10; i++) {
            node_info node = graph.getNode(i);
            assertTrue(neighbors.contains(node));
            
        }
        for (int i = 30; i > 10 ; i--) {
            node_info node = graph.getNode(i);
            assertFalse(neighbors.contains(node));
            
        }
        graph.removeEdge(11,3);
        node_info node = graph.getNode(3);
        assertFalse(neighbors.contains(3));
        node = graph.getNode(11);
        for (int i = 4; i < 11; i++) {
            Collection neighbors2 = graph.getV(i);
            assertTrue(neighbors2.contains(node));
        }
        
        
        

    }
    @Test
    void removeNode2(){
        weighted_graph graph = generateGraph(99);
        for (int i = 0; i < 99 ; i++) {
            graph.connect(i,99,i+5);

        }
        for (int i = 0; i < 99 ; i++) {
            graph.removeEdge(i,99);

        }
        for (int i = 0; i < 99 ; i++) {
            assertEquals(-1,graph.getEdge(i,99));

        }
    }






    public static WGraph_DS generateGraph(int size){
        WGraph_DS ans = new WGraph_DS();
        for (int i = 0; i <= size ; i++) {
            ans.addNode(i);

        }
        return ans;
    }







}