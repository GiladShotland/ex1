package src.ex1.tests;


import ex1.src.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


class WGraph_AlgoTest {

    @Test
    void isConnected1() {
        weighted_graph g = null;
        weighted_graph_algorithms alg = new WGraph_Algo();
        alg.init(g);
        assertTrue(alg.isConnected());
        weighted_graph graph = WGraph_DSTest.generateGraph(1000);

        alg.init(graph);
        for (int i = 0; i < 1000; i++) {
            graph.connect(i, i + 1, 1);
        }
        assertTrue(alg.isConnected());
        graph.removeEdge(3, 4);
        assertFalse(alg.isConnected());
        weighted_graph graph1 = WGraph_DSTest.generateGraph(0);
        alg.init(graph1);
        assertTrue(alg.isConnected());
        graph1.addNode(2);
        assertFalse(alg.isConnected());


    }

    @Test
    void isConnected2() {
        //check for 100 graphs
        for (int i = 0; i < 100; i++) {
            Random x = new Random();
            Random y = new Random();
            Random z = new Random();
            int iterateto = z.nextInt(98);
            weighted_graph graph = WGraph_DSTest.generateGraph(100);
            for (int j = 0; j < iterateto; j++) {
                int a = x.nextInt(100);
                int b = y.nextInt(100);
                graph.connect(a, b, a);
            }
            weighted_graph_algorithms alg = new WGraph_Algo();
            alg.init(graph);
            //less then 99 edges - the graph can't be connected!
            assertFalse(alg.isConnected());
            weighted_graph graph1 = WGraph_DSTest.generateGraph(100);
            for (int j = 0; j < 100; j++) {
                graph1.connect(j, j + 1, j);
                int a = x.nextInt(100);
                int b = y.nextInt(100);
            }
            //this graph have to be connected
            alg.init(graph1);
            assertTrue(alg.isConnected());

        }

    }


    @Test
    void shortestPathDist() {
        weighted_graph g = null;
        weighted_graph_algorithms alg = new WGraph_Algo();
        alg.init(g);
        assertEquals(-1, alg.shortestPathDist(1, 2));
        weighted_graph graph = new WGraph_DS();
        for (int i = 1; i <= 10; i++) {
            graph.addNode(i);

        }
        graph.connect(1, 10, 50);
        graph.connect(1, 4, 2);
        graph.connect(1, 2, 3);
        graph.connect(4, 7, 3);
        graph.connect(4, 8, 9);
        graph.connect(4, 2, 1);
        graph.connect(4, 5, 7.53);
        graph.connect(2, 3, 6);
        graph.connect(3, 5, 4.3232);
        graph.connect(8, 9, 9.34);
        graph.connect(8, 7, 7.65);
        graph.connect(7, 6, 6.5656);
        graph.connect(7, 10, 1);


        alg.init(graph);
        assertEquals(6, alg.shortestPathDist(1, 10));
        assertEquals(alg.shortestPathDist(1, 10), alg.shortestPathDist(10, 1));

        for (int i = 1; i <= 7; i++) {
            graph.addNode(i);

        }
        graph.connect(1, 2, 1);
        graph.connect(1, 3, 2);
        graph.connect(4, 2, 1);
        graph.connect(4, 5, 1);
        graph.connect(5, 6, 1);
        graph.connect(3, 5, 5);
        assertEquals(4, alg.shortestPathDist(1, 6));
        assertEquals(alg.shortestPathDist(1, 4), alg.shortestPathDist(4, 1));

        weighted_graph graph2 = WGraph_DSTest.generateGraph(20);
        alg.init(graph2);
        assertEquals(-1, alg.shortestPathDist(1, 2));
        graph2.connect(1, 2, 1);
        assertEquals(1, alg.shortestPathDist(1, 2));

    }

    @Test
    void shortestPath() {
        weighted_graph graph = WGraph_DSTest.generateGraph(12);
        graph.connect(1, 2, 3.2);
        graph.connect(1, 4, 1.2);
        graph.connect(4, 3, 7.23);
        graph.connect(4, 5, 1.3);
        graph.connect(4, 6, 9.8787);
        graph.connect(4, 8, 1.1001);
        graph.connect(4, 7, 8.6665);
        graph.connect(4, 5, 1);
        graph.connect(5, 6, 2.333);
        graph.connect(7, 8, 9.111);
        graph.connect(8, 9, 4);
        graph.connect(9, 10, 5.33);
        graph.connect(10, 12, 1.34);
        graph.connect(10, 11, 6.55);
        graph.connect(11, 12, 7.65);
        Collection<node_info> path = new LinkedList<node_info>();
        path.add(graph.getNode(1));
        path.add(graph.getNode(4));
        path.add(graph.getNode(8));
        path.add(graph.getNode(9));
        path.add(graph.getNode(10));
        path.add(graph.getNode(12));
        weighted_graph_algorithms alg = new WGraph_Algo();
        alg.init(graph);
        assertEquals(alg.shortestPath(1, 12), path);
        alg.init(null);
        assertEquals(null, alg.shortestPath(1, 2));

    }
    //brute force tests
    @Test
    void shortestPathDist2() {
        weighted_graph graph = WGraph_DSTest.generateGraph(100);
        for (int i = 0; i < 100; i++) {
            graph.connect(i, i + 1, i);

        }
        weighted_graph_algorithms alg = new WGraph_Algo();
        alg.init(graph);
        for (int i = 100; i > 0; i--) {

            assertEquals(sigma(i), alg.shortestPathDist(0, i));

        }
        graph.removeEdge(49, 50);
        for (int i = 100; i > 49; i--) {
            assertEquals(-1, alg.shortestPathDist(0, i));
        }
        for (int i = 49; i > 0; i--) {
            assertEquals(sigma(i), alg.shortestPathDist(0, i));
        }
        graph = WGraph_DSTest.generateGraph(14);
        graph.removeNode(0);
        graph.connect(1, 2, 1);
        graph.connect(1, 4, 2);
        graph.connect(2, 6, 4);
        graph.connect(4, 5, 1);
        graph.connect(4, 6, 3);
        graph.connect(3, 6, 6);
        graph.connect(6, 8, 7);
        graph.connect(6, 11, 60);
        graph.connect(8, 7, 8);
        graph.connect(8, 9, 1);
        graph.connect(9, 10, 10);
        graph.connect(10, 11, 3);
        graph.connect(11, 12, 3);
        graph.connect(12, 14, 11);
        graph.connect(12, 13, 1);
        graph.connect(13, 14, 5);
        alg.init(graph);
        for (int i = 0; i < 14; i++) {
            assertEquals(-1, alg.shortestPathDist(i, i));

        }


        assertEquals(23, alg.shortestPathDist(1, 10));

        assertEquals(23, alg.shortestPathDist(10, 1));

        assertEquals(34, alg.shortestPathDist(14, 2));

        assertEquals(34, alg.shortestPathDist(2, 14));

        assertEquals(21, alg.shortestPathDist(6, 11));

        assertEquals(21, alg.shortestPathDist(11, 6));
        graph.connect(6,11,1);
        assertEquals(7,alg.shortestPathDist(3,11));
        graph.connect(6,11,60);
        graph.removeEdge(9, 10);
        assertEquals(68, alg.shortestPathDist(1, 10));
        assertEquals(68, alg.shortestPathDist(10, 1));
        assertEquals(73, alg.shortestPathDist(14, 2));
        assertEquals(73, alg.shortestPathDist(2, 14));
        assertEquals(60, alg.shortestPathDist(6, 11));
        assertEquals(60, alg.shortestPathDist(11, 6));
        graph.removeEdge(4,5);
        assertEquals(-1,alg.shortestPathDist(4,5));
        weighted_graph graph2 = WGraph_DSTest.generateGraph(10);
        graph2.connect(1,2,0);
        graph2.connect(2,3,0);
        graph2.connect(3,4,0);
        graph2.connect(4,5,0);
        graph2.connect(5,6,0);
        alg.init(graph2);
        assertEquals(0,alg.shortestPathDist(1,6));

    }
    //brute force tests
    @Test
    void shortestPath2() {
        weighted_graph graph = WGraph_DSTest.generateGraph(14);
        weighted_graph_algorithms alg = new WGraph_Algo();
        graph.removeNode(0);
        graph.connect(1, 2, 1);
        graph.connect(1, 4, 2);
        graph.connect(2, 6, 4);
        graph.connect(4, 5, 1);
        graph.connect(4, 6, 3);
        graph.connect(3, 6, 6);
        graph.connect(6, 8, 7);
        graph.connect(6, 11, 60);
        graph.connect(8, 7, 8);
        graph.connect(8, 9, 1);
        graph.connect(9, 10, 10);
        graph.connect(10, 11, 3);
        graph.connect(11, 12, 3);
        graph.connect(12, 14, 11);
        graph.connect(12, 13, 1);
        graph.connect(13, 14, 5);
        alg.init(graph);
           Collection<node_info> path = new LinkedList<node_info>();
        path.add(graph.getNode(2));
        path.add(graph.getNode(6));
        path.add(graph.getNode(8));
        path.add(graph.getNode(9));
        path.add(graph.getNode(10));
        path.add(graph.getNode(11));
        assertEquals(path, alg.shortestPath(2, 11));
        path.clear();
        graph.removeEdge(2, 6);
        path.add(graph.getNode(2));
        path.add(graph.getNode(1));
        path.add(graph.getNode(4));
        path.add(graph.getNode(6));
        path.add(graph.getNode(8));
        path.add(graph.getNode(9));
        path.add(graph.getNode(10));
        path.add(graph.getNode(11));
        assertEquals(path, alg.shortestPath(2, 11));
        path.clear();
        path.add(graph.getNode(12));
        path.add(graph.getNode(13));
        path.add(graph.getNode(14));
        assertEquals(path, alg.shortestPath(12, 14));
        path.clear();
        path.add(graph.getNode(14));
        path.add(graph.getNode(13));
        path.add(graph.getNode(12));
        assertEquals(path, alg.shortestPath(14, 12));
        graph.connect(8, 10, 1.1);
        path.clear();
        path.add(graph.getNode(9));
        path.add(graph.getNode(8));
        path.add(graph.getNode(10));
        assertEquals(path, alg.shortestPath(9, 10));
        graph.removeEdge(8, 10);
        path.clear();
        path.add(graph.getNode(9));
        path.add(graph.getNode(10));
        assertEquals(path, alg.shortestPath(9, 10));
        path.clear();
        graph.removeEdge(9, 10);
        path.add(graph.getNode(9));
        path.add(graph.getNode(8));
        path.add(graph.getNode(6));
        path.add(graph.getNode(11));
        path.add(graph.getNode(10));
        assertEquals(path, alg.shortestPath(9, 10));
        path = null;
        graph.removeEdge(8, 9);
        assertEquals(path, alg.shortestPath(9, 10));



    }
    @Test
    void shortestPathDist3(){
        weighted_graph graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addNode(6);
        graph.connect(1,2,0);
        graph.connect(2,3,0);
        graph.connect(3,4,0);
        graph.connect(4,5,0);
        graph.connect(5,6,0);
        Collection<node_info> path = new LinkedList<node_info>();
        path.add(graph.getNode(1));
        path.add(graph.getNode(2));
        path.add(graph.getNode(3));
        path.add(graph.getNode(4));
        path.add(graph.getNode(5));
        path.add(graph.getNode(6));
        weighted_graph_algorithms alg = new WGraph_Algo();
        alg.init(graph);
        assertEquals(path,alg.shortestPath(1,6));
    }
    @Test
    void copy(){
        weighted_graph g = null;
        weighted_graph_algorithms alg = new WGraph_Algo();
        alg.init(g);
        assertNull(alg.copy());

        weighted_graph graph = WGraph_DSTest.generateGraph(100);
        for (int i = 1; i < 5 ; i++) {
            graph.connect(i,i-1,2);

        }
        alg.init(graph);
        weighted_graph copy = alg.copy();
        boolean equal = copy.equals(graph);
        copy.connect(0,1,2);
        assertEquals(copy,graph);
        copy.removeEdge(0,1);
        assertNotEquals(graph,copy);

    }
    @Test
    void saveload(){
        weighted_graph graph = WGraph_DSTest.generateGraph(20);
        for (int i = 0; i < 20; i++) {
            graph.connect(i,i+1,i);

        }

        weighted_graph_algorithms alg = new WGraph_Algo();
        alg.init(graph);
        assertFalse(alg.save(""));
        assertTrue(alg.save("oop"));
        alg.init(null);
        assertFalse(alg.load(""));
        assertFalse(alg.load("gilad"));
        assertTrue(alg.load("oop"));
        assertEquals(graph,alg.getGraph());
    }


    private double sigma(double num) {
        double ans = 0;
        for (int i = 0; i < num; i++) {
            ans += i;
        }
        return ans;
    }


}