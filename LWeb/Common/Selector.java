package LWeb.Common;

import LWeb.Common.Attribute.AttributeStyle;
import static LWeb.Common.Attribute.CheckType.*;
import static LWeb.Common.EllClass.Activity.ENABLED;
import static LWeb.Common.EllClass.Activity.STATIC;
import static LWeb.Common.Selector.Combinators.DESCENDANT;
import LWeb.Common.Tree.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;


public class Selector {
    /*
    target, searach type-">, +, ..."
    target: tag, id, classes, atributes, function specifiers (:has, :contains,...)
    
    //qualify-> like has except all has to be true
    types:
    0-normal: tag, id, classes, atributes
    1-contains: 0+ strings
    2-text: 0+ text types (lang, ...)
    3-of-type: 0+ An+B
    4-nth: 3+ selector list
    5-has: 0+ selector list
    6-not: 5
    7-qualify: 5

    " ": for all child nodes check condition set and continue
    ">": for all immediate children of current node
    "+": for immidiate sibling of current node
    "~": for all siblings of current node
    *///                        " "       ">"   "+"    "~"
    public enum Combinators{DESCENDANT, CHILD, NEXT, SUBSEQUENT}
    //+ has -> selesctor list, nth-child -> counter + selector, contains -> text selector(works with variables)
    public final ArrayList<Pair<Combinators,ArrayList<Conditions>>> conditions = new ArrayList<>();//sort based on speed of elimination
    public static Conditions CondTrue=new CondTrue();
    
    public Selector(){
        
    }
    public void addCondition(Pair<Combinators,ArrayList<Conditions>> c){
        conditions.add(c);
    }
    
    public boolean validate(Node<ElementTag> ell, boolean capturing) {
        if(capturing){
            return subValCap(ell,conditions.size()-1);
        }else{
            return subVal(ell,0);
        }
    }
    private boolean subValCap(Node<ElementTag> ell, int index){//doa in reverse
        if(index==0&&conditions.get(0).first!=DESCENDANT)return false;
        if(index<1)return true;
        Combinators cm = conditions.get(index).first;
        ArrayList<Conditions> ac = conditions.get(index-1).second;
        switch(cm){
            case DESCENDANT:
                Node<ElementTag> nop=null;
                boolean parv=true;
                do{
                    nop=ell.getParent();
                    if(nop==null)return false;
                    for (int j = 0; j < ac.size()&&parv; j++) {parv = ac.get(j).validate(nop);}
                    if(parv){
                        parv=subVal(nop, index-1);
                        if(parv)return true;
                    }
                }while(!parv);
                return false;
            case CHILD:
                Node<ElementTag> nd=ell.getParent();
                if(nd==null)return false;
                boolean itr=true;
                for (int j = 0; j < ac.size()&&itr; j++) {itr = ac.get(j).validate(nd);}
                if(itr)itr = subVal(nd, index-1);
                return itr;
            case NEXT:
                Node<ElementTag> node_nx = ell.getParent().getNode(ell.getParentIndex()-1);
                if(node_nx==null){return false;}
                boolean nxb=true;
                for (int j = 0; j < ac.size()&&nxb; j++) {nxb = ac.get(j).validate(node_nx);}
                if(nxb) nxb = subVal(node_nx, index-1);
                return nxb;
            case SUBSEQUENT:
                Node<ElementTag> node_sq=null;
                Node<ElementTag> parent_sq=ell.getParent();
                int j=ell.getParentIndex();
                boolean acc_sq=true;
                do{
                    node_sq = parent_sq.getNode(j-1);
                    if(node_sq==null){return false;}
                    for (int k = 0; k < ac.size()&&acc_sq; k++) {acc_sq = ac.get(k).validate(node_sq);}
                    if(acc_sq)acc_sq=subVal(node_sq, index-1);
                    j--;
                }while(!acc_sq);
                return true;
        }
        return true;
    }
    
    private boolean subVal(Node<ElementTag> ell, int index){//non capturing
        if(index>=conditions.size())return true;
        Combinators cm = conditions.get(index).first;
        ArrayList<Conditions> ac = conditions.get(index).second;
        switch(cm){
            case DESCENDANT:
                for(Node<ElementTag> nd:Tree.iterate(ell)){
                    boolean itr=true;
                    for (int j = 0; j < ac.size()&&itr; j++) {itr = ac.get(j).validate(nd);}
                    if(itr){
                        itr=subVal(nd, index+1);
                        if(itr)return true;
                    }
                }
                return false;
                /*for(Node<ElementTag> nd:Tree.iterate(ell, (Node<ElementTag> el)->{
                        boolean itr=true;
                        for (int j = 0; j < ac.size()&&itr; j++) {itr = ac.get(j).validate(el);}
                        return itr?0:-1;
                    })){
                    boolean itr=true;
                    itr=subVal(nd, index+1);
                    if(itr)return true;
                }
                return false;*/
            case CHILD:
                for(Node<ElementTag> nd:ell){
                    boolean itr=true;
                    for (int j = 0; j < ac.size()&&itr; j++) {itr = ac.get(j).validate(nd);}
                    if(itr){
                        itr=subVal(nd, index+1);
                        if(itr)return true;
                    }
                }
                return false;
            case NEXT:
                Node<ElementTag> node_nx = ell.getParent().getNode(ell.getParentIndex()+1);
                if(node_nx==null){return false;}
                boolean nxb=true;
                for (int j = 0; j < ac.size()&&nxb; j++) {nxb = ac.get(j).validate(node_nx);}
                if(nxb)nxb=subVal(node_nx, index+1);
                return nxb;
            case SUBSEQUENT:
                Node<ElementTag> node_sq=null;
                Node<ElementTag> parent_sq=ell.getParent();
                int j=ell.getParentIndex();
                boolean acc_sq=true;
                do{
                    node_sq = parent_sq.getNode(j+1);
                    if(node_sq==null){return false;}
                    for (int k = 0; k < ac.size()&&acc_sq; k++) {acc_sq = ac.get(k).validate(node_sq);}
                    if(acc_sq)acc_sq=subVal(node_sq, index+1);
                    j++;
                }while(!acc_sq);
                return true;
        }
        return true;
    }
    
    //add specificity
    public interface Conditions{public boolean validate(Node<ElementTag> ell);}
    public static class CondTrue implements Conditions{
        public CondTrue(){}
        public boolean validate(Node<ElementTag> ell) {return true;}
        public String toString(){return "\u2713";}
    }
    public static class CondTag implements Conditions{
        final String tag;
        public CondTag(String tag){
            this.tag=tag;
        }
        public boolean validate(Node<ElementTag> ell) {
            return tag.equals(ell.data.tag);
        }
        public String toString(){return ""+tag;}
    }
    public static class CondPEll implements Conditions{
        final String tag;
        public CondPEll(String tag){
            this.tag=tag;
        }
        public boolean validate(Node<ElementTag> ell) {
            return tag.equals(ell.data.tag);
        }
        public String toString(){return "::"+tag;}
    }
    public static class CondId implements Conditions{
        final String id;
        public CondId(String id){
            this.id=id;
        }
        public boolean validate(Node<ElementTag> ell) {
            return id.equals(ell.data.id);
        }
        public String toString(){return "#"+id;}
    }
    public static class CondClass implements Conditions{
        final String cls;
        public CondClass(String cls){
            this.cls=cls;
        }
        public boolean validate(Node<ElementTag> ell) {
            EllClass cs = ell.data.getClass(cls);
            return cs==null?false:true;//do something to account for inactive classess
        }
        public String toString(){return "."+cls;}
    }
    public static class CondAttribute implements Conditions{
        final AttributeStyle atr;
        public CondAttribute(AttributeStyle atr){
            this.atr=atr;
        }
        public boolean validate(Node<ElementTag> ell) {
            Attribute at = ell.data.getAtribute(atr.name);
            return at==null?false:atr.equals(at);
        }
        public String toString(){return "["+atr+"]";}
    }
    public static class CondHas implements Conditions{
        final ArrayList<Selector> has;
        public CondHas(ArrayList<Selector> has){
            this.has=has;
        }
        public boolean validate(Node<ElementTag> ell) {
            boolean b=true;
            for(Selector sl:has){
                b=false;
                if(sl.validate(ell,false)){return true;}
            }
            return b;
        }
        public String toString(){return ":has("+has+")";}
    }
    public static class CondQualify implements Conditions{
        final ArrayList<Selector> qlf;
        public CondQualify(ArrayList<Selector> qlf){
            this.qlf=qlf;
        }
        public boolean validate(Node<ElementTag> ell) {
            for(Selector sl:qlf){
                if(!sl.validate(ell,false)){return false;}
            }
            return true;
        }
        public String toString(){return ":qualify("+qlf+")";}
    }
    public static class CondIs implements Conditions{
        final ArrayList<ArrayList<Conditions>> is;
        public CondIs(ArrayList<ArrayList<Conditions>> is){
            this.is=is;
        }
        public boolean validate(Node<ElementTag> ell) {
            for(ArrayList<Conditions> sl:is){
                boolean fl=true;
                for(Conditions cs:sl){
                    fl = fl&&cs.validate(ell);
                }
                if(fl){return true;}
            }
            return false;
        }
        public String toString(){return ":is("+is+")";}
    }
    public static class CondNot implements Conditions{
        final ArrayList<Selector> not;
        public CondNot(ArrayList<Selector> not){
            this.not=not;
        }
        public boolean validate(Node<ElementTag> ell) {
            for(Selector sl:not){
                if(sl.validate(ell,false)){return false;}
            }
            return true;
        }
        public String toString(){return ":not("+not+")";}
    }
    //contains  -> text, regex -> /<patern>/, /<patern>/i 
    public static class CondContains implements Conditions{
        final Predicate<String> pattern;
        public CondContains(String pattern, boolean caseSensitivity){
            this.pattern = Pattern.compile( pattern,
                                    caseSensitivity
                                        ?Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE 
                                        :0).asPredicate();
        }
        public boolean validate(Node<ElementTag> ell) {
            for(Node<ElementTag> el:Tree.iterate(ell, (Node<ElementTag> el)->{
                    return el.data.textOnly?0:-1;
                })){
                if(pattern.test(el.data.tag)){return true;}
            }
            return false;
        }
        public String toString(){return ":contains("+pattern+")";}
    }
    //nth-child -> An+B/even/odd,   2n+1 -> sc=2;of=0,   2n -> sc=2;of=sc-1      
    public static class CondNth implements Conditions{
        int scale;
        int offset;
        final ArrayList<ArrayList<Conditions>> of;
        public CondNth(int scale, int offset, ArrayList<ArrayList<Conditions>> of){
            this.scale=scale;
            this.offset=offset;
            this.of=of;
        }
        public boolean validate(Node<ElementTag> ell) {
            Node<ElementTag> parent = ell.getParent();
            if(parent==null)return false;
            float ipos=(scale!=0)
                    ?((offset<0)
                        ?(-(ell.getParentIndex()-parent.getNumChildren())+offset)*1.0f/scale
                        :(ell.getParentIndex()-offset+1)*1.0f/scale)
                    :((offset<0)
                        ?(ell.getParentIndex()-offset==parent.getNumChildren()?0:-1)
                        :ell.getParentIndex()+1==offset?0:-1);
            if(!(ipos%1==0&&ipos>=0))return false;
            if(of.isEmpty())return true;
            for(ArrayList<Conditions> sl:of){
                boolean fl=true;
                for(Conditions cs:sl){
                    fl = fl&&cs.validate(ell);
                }
                if(fl){return true;}
            }
            return false;
        }
        public String toString(){return ":nth-child("+scale+"n"+((offset>=0)?"+"+offset:offset)+" "+of+")";}
    }
    //patch for per type
    public static class CondNthType implements Conditions{
        int scale;
        int offset;
        final ArrayList<ArrayList<Conditions>> of;
        public CondNthType(int scale, int offset, ArrayList<ArrayList<Conditions>> of){
            this.scale=scale;
            this.offset=offset;
            this.of=of;
        }
        public boolean validate(Node<ElementTag> ell) {
            Node<ElementTag> parent = ell.getParent();
            if(parent==null)return false;
            float ipos=(scale!=0)
                    ?((offset<0)
                        ?(-(ell.getParentIndex()-parent.getNumChildren())+offset)*1.0f/scale
                        :(ell.getParentIndex()-offset+1)*1.0f/scale)
                    :((offset<0)
                        ?(ell.getParentIndex()-offset==parent.getNumChildren()?0:-1)
                        :ell.getParentIndex()+1==offset?0:-1);
            if(!(ipos%1==0&&ipos>=0))return false;
            if(of.isEmpty())return true;
            for(ArrayList<Conditions> sl:of){
                boolean fl=true;
                for(Conditions cs:sl){
                    fl = fl&&cs.validate(ell);
                }
                if(fl){return true;}
            }
            return false;
        }
        public String toString(){return ":nth-child("+scale+"n"+((offset>=0)?"+"+offset:offset)+" "+of+")";}
    }
    
    //nth-of-type -> An+B/even/odd for every type(tag)     similar to CondNth except count all tags of same type

    
    public String toString(){return ""+conditions;}
}
