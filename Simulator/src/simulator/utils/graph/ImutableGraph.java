/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.utils.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Roman Vais
 */
public interface ImutableGraph<T> {

    public Node<T> getRoot();
    public List<Edge<T>> getEdgesLinkedTo(Node<T> node);
    public List<Node<T>> getAllNodes();

}
