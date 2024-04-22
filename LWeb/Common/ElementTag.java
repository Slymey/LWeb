package LWeb.Common;

import LWeb.Common.Attribute.AttributeEl;
import static LWeb.Common.Common.ats;
import LWeb.Common.EllClass.Activity;
import static LWeb.Common.EllClass.Activity.*;
import LWeb.Compiler.Parser;
import LWeb.Compiler.Parser.TokenType;
import static LWeb.Compiler.Parser.TokenType.*;
import static LWeb.Compiler.Parser.tokenize;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

public class ElementTag {
    public boolean textOnly=false;
    public int closingType=0;//-1-closed, 0-no closing, 1-self closing, 2-re opening, 3-contained
    public String tag="";
    public String id="";
    //public ArrayList<ElementNode> nodes = new ArrayList<>();
    public HashMap<String,AttributeEl> atributes = new HashMap<>();
    public HashMap<String,EllClass> classes = new HashMap<>();
    //public ElementNode parent = null;
    //HashMap<String,StyleProperty> styles = new HashMap<>();
    
    public ElementTag(String tag, boolean textOnly){
        this.tag=tag;
        this.textOnly=textOnly;
    }
    public ElementTag(String tag, boolean textOnly, int clt){
        this.tag=tag;
        this.textOnly=textOnly;
        this.closingType=clt;
    }
    public void addClass(EllClass cl){
        classes.put(cl.name, cl);
    }
    public void addClass(String classes){
        Pair<String[], TokenType[]> tk = tokenize(classes);
        String sa[] = tk.getFirst();
        TokenType ta[] = tk.getSecond();
        boolean pmk = false;
        TokenType ptr = null;
        for(int i=0;i < sa.length;i++){
            String sr = sa[i];
            TokenType tr = ta[i];
            if(i==0&&("\"".equals(sr)||"'".equals(sr))){continue;}
            if(tr==AMPERSANT || tr==PERCENT){
                pmk = true;
            }else if(tr==TEXT){
                if(!pmk || ptr==WHITESPACE) {
                    addClass(new EllClass(sr, STATIC));
                }else if(pmk && ptr==AMPERSANT) {
                    addClass(new EllClass(sr, ENABLED));
                }else if(pmk && ptr==PERCENT) {
                    addClass(new EllClass(sr, DISABLED));
                }
                pmk = false;
            }
            ptr = tr;
        }
    }
    public void addAtribute(String key, AttributeEl value){
        atributes.put(key, value);
    }
    public void addAtribute(Pair<String,AttributeEl> atribute){
        atributes.put(atribute.getFirst(), atribute.getSecond());
    }
    
    public void removeAtribute(String key){
        atributes.remove(key);
    }
    
    public EllClass getClass(String key){
        return classes.get(key);
    }
    public AttributeEl getAtribute(String key){
        return atributes.get(key);
    }
    /*
    public void addNode(ElementNode element){
        nodes.add(element);
    }
    public void setNode(int index, ElementNode element){
        nodes.set(index, element);
    }
    public void setParent(ElementNode element){
        parent = element;
    }
    public void insertNode(int index, ElementNode element){
        nodes.add(index, element);
    }*/

    
    @Override
    public String toString(){
        return (textOnly?"\\":tag)+ "|"+textOnly+"|"+id+"|"+closingType+((atributes.isEmpty())?"":" "+atributes)+" "+((classes.isEmpty())?"":" "+classes);
    }
    
    
    
    
    private class StyleProperty {

        public StyleProperty() {
            
        }
    }
}
