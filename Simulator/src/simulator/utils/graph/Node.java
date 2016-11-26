/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.utils.graph;

import java.util.Objects;

/**
 *
 * @author Roman Vais
 * @param <T>
 */
public class Node<T> {

    private final T o;

    public Node(T o) {
        this.o = o;
    }

    public T getObject() {
        return o;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Node) {
            return ((Node) o).getObject().equals(this.o);
        }
        return this.o.equals(o);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.o);
        return hash;
    }

}
