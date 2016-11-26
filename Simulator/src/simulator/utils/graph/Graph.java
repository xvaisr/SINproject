/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.utils.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Roman Vais
 * @param <T>
 */
public class Graph<T> {

    private Node<T> root;
    private HashMap<Node<T>, List<Edge<T>>> nodeMapings;


    public Graph() {
        this(new Node(null));
    }

    public Graph(Node<T> root) {
        this.root = root;
        this.nodeMapings = new HashMap();
    }

    public Node<T> getRoot() {
        return this.root;
    }

    public void linkObject(Node <T> root, T object) {
        this.linkNode(root, new Node<T>(object));
    }

    public void linkNode(Node <T> root, Node <T> node) {
        Edge<T> e = new Edge<T>(root, node);

        mapNodesToEdges(root, e);
        mapNodesToEdges(node, e);
    }

    private void mapNodesToEdges(Node<T> n, Edge<T> e) {
        if (this.nodeMapings.containsKey(n)) {
            this.nodeMapings.get(n).add(e);
        }
        else {
            List <Edge<T>> list = new ArrayList();
            list.add(e);
            this.nodeMapings.put(n, list);
        }
    }

    public List<Edge<T>> getEdgesLinkedTo(Node<T> node) {
        if (this.nodeMapings.containsKey(node)) {
            return Collections.unmodifiableList(this.nodeMapings.get(node));
        }
        return Collections.emptyList();
    }

    public List<Node<T>> getAllNodes() {
        List<Node<T>> nodeList = new LinkedList();
        nodeList.addAll(this.nodeMapings.keySet());
        return Collections.unmodifiableList(nodeList);
    }


}
