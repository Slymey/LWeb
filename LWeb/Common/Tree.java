package Common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Tree<T> {
    final public Node<T> root;

    public Tree(T rootData) {
        root = new Node<>(null, rootData,0);
    }
    

    public static class Node<T> {
        public T data;
        private Node<T> parent;
        private int indexInParent;
        private List<Node<T>> children;
        
        private Node(Node<T> parent, T data, int idx){
            this.parent=parent;
            this.data=data;
            this.children = new ArrayList<>();
            this.indexInParent=idx;
        }
        
        public void addNode(T data){
            children.add(new Node<>(this, data,children.size()));
        }
        public void moveNode(Node<T> parent){
            if(this.parent!=null){
                this.parent.children.remove(indexInParent);
                this.parent=parent;
            }
            indexInParent=parent.children.size();
            parent.children.add(this);
        }
        
        public Node<T> findFirstChild(T t){
            for(Node<T> n:children){
                if(n.data.equals(t))return n;
            }
            return null;
        }
        public Node<T> findFirstChild(T t, Comparator<T> c){
            for(Node<T> n:children){
                if(c.compare(n.data,t)==0)return n;
            }
            return null;
        }
        public Node<T> findFirstChild(T t, Comparable<T> c){
            for(Node<T> n:children){
                if(c.equals(n.data))return n;
            }
            return null;
        }
    }
}
