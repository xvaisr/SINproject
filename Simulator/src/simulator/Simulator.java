/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator;

import java.util.List;
import simulator.utils.graph.Edge;
import simulator.utils.graph.Graph;
import simulator.utils.graph.Node;

/**
 *
 * @author rvais
 */
public class Simulator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Node<Integer> nodeA, nodeB;
        nodeA = new Node<>(1);
        nodeB = new Node<>(2);
        Graph g = new Graph(nodeA);

        g.linkNode(nodeA, nodeB);
        nodeB = new Node<>(3);
        g.linkNode(nodeA, nodeB);

        List<Edge<Integer>> l = g.getEdgesLinkedTo(nodeA);
        for (Edge<Integer> e : l) {
            System.out.println("Edge links nodes <" + e.getNode(true).getObject().toString() + "> and <" +
                    e.getNode(false).getObject().toString() + "> together.");
        }

    }

}
