/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.utils.graph;

/**
 *
 * @author Roman Vais
 */
public interface Graph<T> extends ImutableGraph<T>{
    public void linkObject(Node <T> root, T object);
    public void linkNode(Node <T> root, Node <T> node);

}
