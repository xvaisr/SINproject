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
 * Implementation of general type of graph that requires unique values to be inserted.
 * @author Roman Vais
 * @param <T>
 */
public class InternalGraph<T> implements Graph<T>{

    private Node<T> root;
    private HashMap<Node<T>, List<Edge<T>>> nodeMapings;


    public InternalGraph() {
        this(new Node(null));
    }

    public InternalGraph(Node<T> root) {
        this.root = root;
        this.nodeMapings = new HashMap();
    }

    @Override
    public Node<T> getRoot() {
        return this.root;
    }

    @Override
    public void linkObject(Node <T> root, T object) {
        this.linkNode(root, new Node<>(object));
    }

    @Override
    public void linkNode(Node <T> root, Node <T> node) {
        Edge<T> e = new Edge<>(root, node);

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

    @Override
    public List<Edge<T>> getEdgesLinkedTo(Node<T> node) {
        if (this.nodeMapings.containsKey(node)) {
            return Collections.unmodifiableList(this.nodeMapings.get(node));
        }
        return Collections.emptyList();
    }

    @Override
    public List<Edge<T>> getEdgesLinkedTo(T object) {
<<<<<<< HEAD
        return getEdgesLinkedTo(new Node<>(object));
=======
        return this.getEdgesLinkedTo(new Node<>(object));
>>>>>>> ea2c3936756570c323e07f9b71fda49e6c1a4a03
    }

    @Override
    public List<Node<T>> getAllNodes() {
        List<Node<T>> nodeList = new LinkedList();
        nodeList.addAll(this.nodeMapings.keySet());
        if (!nodeList.contains(this.root)) {
            nodeList.add(this.root);
        }
        return Collections.unmodifiableList(nodeList);
    }


}
