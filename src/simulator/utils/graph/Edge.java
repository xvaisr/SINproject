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
 */
public class Edge<T> {

    private Node<T> a;
    private Node<T> b;

    public Edge(Node<T> a, Node<T> b) {
        this.a = a;
        this.b = b;
    }

    public Node<T> getNode(boolean left) {
        return ((left)? this.a : this.b);
    }

    public boolean doesLinkThis(T object) {
        return (this.a.equals(object) || this.b.equals(object));
    }

    public boolean doesLinkThis(Node<T> node) {
        return (this.a.equals(node) || this.b.equals(node));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Edge) {
            return ((Edge) o).doesLinkThis(this.a) && ((Edge) o).doesLinkThis(this.b);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.a);
        hash = 89 * hash + Objects.hashCode(this.b);
        return hash;
    }


}
