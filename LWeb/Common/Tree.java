package LWeb.Common;

import static LWeb.Common.Common.flatten;
import static LWeb.Common.Common.lognm;
import static LWeb.Common.Common.sopl;
import static LWeb.Common.Pair.Pair;
import LWeb.Common.Tree.Node;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Tree<T> implements Iterable<Node<T>>{
    private final static Tree sttree = new Tree(null,false);
    final public Node<T> root;
    final private boolean ktos;

    public Tree(T rootData, boolean keepTrackOfSize) {
        ktos=keepTrackOfSize;
        root = new Node<>(null, rootData,0);
        root.size=(ktos)?0:-1;
    }
    
    //<editor-fold defaultstate="collapsed" desc="TreeConditionalIteratorDepth">
    public class TreeConditionalIteratorDepth implements Iterator<Node<T>>, Iterable<Node<T>>{
        Node<T> current;
        Iterator<Node<T>> nodeItr=null;//all subtrees
        Iterator<Node<T>> curItr=null;//the subtree
        Comparable<Node<T>> c;
        public TreeConditionalIteratorDepth(Node<T> root, Comparable<Node<T>> c) {
            this.c=c;
            current=root;
            nodeItr=root.iterator();
        }
        @Override
        public boolean hasNext() {
            if(nodeItr==null)return false;
            if(!nodeItr.hasNext()){
                nodeItr=null;
                return c.compareTo(current)==0;
            }
            
            if(curItr==null||!curItr.hasNext()){
                Node<T> tmp = nodeItr.next();
                curItr=new TreeConditionalIteratorDepth(tmp, c);
            }
            
            return true;
        }
        @Override
        public Node<T> next() {
            if(nodeItr==null)return current;
            return curItr.next();
        }
        @Override
        public Iterator<Node<T>> iterator() {
            return this;
        }
    }
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="TreeIteratorDepth">
    public class TreeIteratorDepth implements Iterator<Node<T>>, Iterable<Node<T>>{
        Node<T> current;
        Iterator<Node<T>> nodeItr=null;//all subtrees/children
        Iterator<Node<T>> curItr;//the subtree
        public TreeIteratorDepth(Node<T> root) {
            current=root;
            nodeItr=root.iterator();
            if(nodeItr.hasNext()){
                curItr = new TreeIteratorDepth(nodeItr.next());
            }
        }
        @Override
        public boolean hasNext() {
            if(nodeItr==null)return false;
            
            if(curItr!=null&&curItr.hasNext())return true;
            if(!nodeItr.hasNext()){
                nodeItr=null;
                return true;
            }
            if(nodeItr.hasNext()){
                curItr = new TreeIteratorDepth(nodeItr.next());
            }
            if(curItr.hasNext())return true;
            
            return false;
        }
        @Override
        public Node<T> next() {
            if(nodeItr==null)return current;
            return curItr.next();
        }
        @Override
        public Iterator<Node<T>> iterator() {
            return this;
        }
        public String toString(){return "##---##";}
    }
    //</editor-fold>
    

    //<editor-fold defaultstate="collapsed" desc="Node">
    public static class Node<T> implements Iterable<Node<T>>{
        public T data;
        private Node<T> parent;
        private int indexInParent;
        private int size;
        private int itrI=0;
        private List<Node<T>> children;
        
        private Node(Node<T> parent, T data, int idx){
            this.parent=parent;
            this.data=data;
            this.children = new ArrayList<>();
            this.indexInParent=idx;
            this.size=0;
        }
        
        public Node<T> addNode(T data){
            Node<T> n = new Node<>(this, data,children.size());
            n.size=(size==-1)?-1:0;
            children.add(n);
            Node<T> ns = n;
            if(size!=-1){
                while(ns.parent!=null){
                    ns=ns.parent;
                    ns.size++;
                }
            }
            return n;
        }
        public void moveNode(Node<T> parent){
            if(this.parent!=null){
                this.parent.children.remove(indexInParent);
                this.parent=parent;
            }
            indexInParent=parent.children.size();
            parent.children.add(this);
        }
        
        public Node<T> getParent(){
            return parent;
        }
        public int getParentIndex(){
            return indexInParent;
        }
        
        public int getSize(){
            return size;
        }
        public Node<T> getNode(int idx){
            if(idx<0||idx>=children.size())return null;
            return children.get(idx);
        }
        
        public int getNumChildren(){
            return children.size();
        }
        public List<Node<T>> getChildren(){
            return Collections.unmodifiableList(children);
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
                if(c.compareTo(n.data)==0)return n;
            }
            return null;
        }
        public Pair<Node<T>,Integer> findFirstNode(Comparable<T> c, int start, int idx){
            if(idx>=start){
                if(c.compareTo(data)==0)return Pair(this, idx);
            }
            System.out.println(data+"-:"+idx);
            idx++;
            for(Node<T> n:children){
                System.out.println("aaa: "+n);
                Pair<Node<T>, Integer> nd = n.findFirstNode(c, start, idx);
                idx=nd.getSecond();
                if(nd.getFirst()!=null)return nd;
            }
            return Pair(null, idx);
        }
        
        private void toStringList(ArrayList<String> al, int indent){
            String ca[] =new String[indent];
            Arrays.fill(ca, "| ");
            al.add(flatten(ca)+data+"\n");
            for(Node<T> n:children){
                n.toStringList(al, indent+1);
            }
        }
        public String toString(){
            return ""+data;
        }

        @Override
        public Iterator<Node<T>> iterator() {
            return children.iterator();
        }
    }
    //</editor-fold>
    
    public String toString(){
        String out="";
        ArrayList<String> al = new ArrayList<>((ktos)?root.size:32);
        root.toStringList(al, 0);
        for(String s:al){
            out+=s;
        }
        return out;
    }
    @Override
    public Iterator<Node<T>> iterator() {
        return (Iterator<Node<T>>) new TreeIteratorDepth(root);
    }
    public static <T> Iterable<Node<T>> iterate(Node<T> nd) {
        return sttree.new TreeIteratorDepth(nd);
    }
    public static <T> Iterable<Node<T>> iterate(Node<T> nd, Comparable<Node<T>> c) {
        return sttree.new TreeConditionalIteratorDepth(nd, c);
    }
    
    
    
    
    
    public static void main(String[] args) {
        Tree<String> tr = new Tree<>("_1", false);
        Node<String> a1 = tr.root.addNode("a1");
        Node<String> b1 = a1.addNode("b1");
        Node<String> b2 = a1.addNode("b2");
        Node<String> b3 = a1.addNode("b3");
        Node<String> a2 = tr.root.addNode("a2");
        Node<String> b1_1 = a2.addNode("b1_1");
        Node<String> c1 = b1_1.addNode("c1");
//        Node<String> b1_2 = a2.addNode("b1_2");
//        Node<String> c1_1 = b1_1.addNode("c1_1");
        Node<String> a3 = tr.root.addNode("a3");
        // b1 b2 b3 a1 c1 b1_1 a2 a3 _1
        sopl(tr);
        String s="";
        for(Iterator<Node<String>> i=tr.iterator();i.hasNext();){
            s=i.next().data ;
            Common.sopl(""+s);
        }
        //rSystem.out.println(lognm()+""+current.data+" "+current.children+" "+curItr+" "+nodeItr);
    }
}
