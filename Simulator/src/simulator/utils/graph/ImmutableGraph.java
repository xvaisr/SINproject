/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.utils.graph;

import java.util.List;

/**
 * Interface representing common type of graph, where nodes contain given type of object.
 * Depending on implementation, graph can be oriented with directional edges or general graph
 * with bidirectional edges. ImmutableGraph interface doesn't expose implementation of methods
 * that can modify graph.
 * @param <T> values
 *
 * @author Roman Vais
 */
public interface ImmutableGraph<T> {

    /**
     * Returns root node of the graph.
     *
     * @return root node of graph
     */
    public Node<T> getRoot();

    /**
     * Returns list of edges that connect given node to others. If implementation is oriented graph
     * and edge(A, B) exists, you will get this edge in a list when node A is given but not when
     * node B is given. Depending on implementation nodes might be required to be unique.
     *
     * @param node Node equal to some node in a graph.
     * @return list of edges connecting given node to other edges.
     */
    public List<Edge<T>> getEdgesLinkedTo(Node<T> node);

    /**
     * Returns list of edges that connect node containing given object to other nodes. If implementation
     * is oriented graph and edge(A, B) exists, you will get this edge in a list when node A is given
     * but not when node B is given. Depending on implementation object contained in nodes might be required
     * to be unique.
     *
     * @param object object stored in some node in graph
     * @return list of edges connecting given node to other edges.
     */
    public List<Edge<T>> getEdgesLinkedTo(T object);

    /**
     * Returns list of all nodes contained in this graph.
     *
     * @return list of all nodes
     */
    public List<Node<T>> getAllNodes();

}
