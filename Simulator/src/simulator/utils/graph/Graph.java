/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.utils.graph;

/**
 * Interface representing common type of graph, where nodes contain given type of object.
 * Depending on implementation, graph can be oriented with directional edges or general graph
 * with bidirectional edges. Graph interface exposes implementation of methods
 * that can modify graph.
 * @param <T> values
 *
 * @author Roman Vais
 */

public interface Graph<T> extends ImutableGraph<T>{

    /**
     * Method links new node containing given object with edge to already existing node.
     * If node doesn't exist in a graph nothing happens. If graph is oriented edge is connected
     * only in one direction.
     *
     * @param root existing node to which you are
     * @param object object to put in a graph
     */
    public void linkObject(Node <T> root, T object);

    /**
     * Method links given node with edge to already existing node.
     * If node doesn't exist in a graph nothing happens. If graph is oriented edge is connected
     * only in one direction.
     *
     * @param root existing node to which you are
     * @param node node to connect in a graph
     */
    public void linkNode(Node <T> root, Node <T> node);

}
