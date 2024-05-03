package LWeb.Compiler.Components;

import LWeb.Compiler.Components.Attribute.AttributeEl;
import LWeb.Compiler.Components.ClassList.Activity;
import LWeb.Common.Pair;
import static LWeb.Compiler.Components.ClassList.Activity.*;
import LWeb.Compiler.Components.StyleProperty.Property;
import LWeb.Compiler.Parser.TokenType;
import static LWeb.Compiler.Parser.TokenType.*;
import static LWeb.Compiler.Parser.tokenize;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class ElementTag {
    public boolean textOnly=false;
    public int closingType=0;//-1-closed, 0-no closing, 1-self closing, 2-re opening, 3-contained
    public String tag="";
    public String id="";
    //public ArrayList<ElementNode> nodes = new ArrayList<>();
    public HashMap<String,AttributeEl> atributes = new HashMap<>();
    public HashMap<ClassList,Activity> classes = new HashMap<>();
    public LinkedList<Pair<ArrayList<ClassCondition>, LinkedHashSet<Property>>> style = new LinkedList<>();
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
    public void addClass(String cl, Activity ac){
        classes.put(ClassList.makeClass(cl), ac);
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
                    addClass(sr, STATIC);
                }else if(pmk && ptr==AMPERSANT) {
                    addClass(sr, ENABLED);
                }else if(pmk && ptr==PERCENT) {
                    addClass(sr, DISABLED);
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
    
    public ClassList getClass(String key){
        ClassList cl=ClassList.getClass(key);
        boolean b = classes.containsKey(cl);
        return b?cl:null;
    }
    public AttributeEl getAtribute(String key){
        return atributes.get(key);
    }
    
    public Activity type(ClassList cl){
        return classes.get(cl);
    }
    public void activate(ClassList cl){
        classes.replace(cl, ENABLED);
    }
    public void deactivate(ClassList cl){
        classes.replace(cl, DISABLED);
    }
    public void toggleActive(ClassList cl){
        Activity ac = type(cl);
        if(ac==ENABLED){
            classes.replace(cl, DISABLED);
        }else if(ac==DISABLED){
            classes.replace(cl, ENABLED);
        }
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
        return (textOnly?"\\":tag)+ "|"+textOnly+"|"+id+"|"+closingType+((atributes.isEmpty())?"":" "+atributes)+" "+((classes.isEmpty())?"":" "+classes)+" "+((style.isEmpty())?"":" "+style);
    }
    
    
    
    
    private class StyleProperty {

        public StyleProperty() {
            
        }
    }
}
