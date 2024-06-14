package LWeb.Compiler.Components;

import static LWeb.Common.Common.byi;
import static LWeb.Common.Common.flatten;
import static LWeb.Common.Common.sg;
import static LWeb.Compiler.Components.StylePropertyList.getByName;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.BiFunction;


public class LayoutPropertyList {
    static HashSet<LayoutProperty> list = sg(()->{
        HashSet<LayoutProperty> al= new HashSet<>();
        list=al;
        al.add(new LayoutProperty("o-left"));
        al.add(new LayoutProperty("o-top"));
        al.add(new LayoutProperty("o-width"));
        al.add(new LayoutProperty("o-height"));
        al.add(new LayoutProperty("parent"));
        al.add(new LayoutProperty("c-left"));
        al.add(new LayoutProperty("c-top"));
        al.add(new LayoutProperty("c-width"));
        al.add(new LayoutProperty("c-height"));
        al.add(new LayoutProperty("text"));
        al.add(new LayoutProperty("bacground-color"));
        al.add(new LayoutProperty("text-color"));
        al.add(new LayoutProperty("text-size"));
        al.add(new LayoutProperty("editable-text"));
        al.add(new LayoutProperty("text-id"));
        al.add(new LayoutProperty("border-top"));
        al.add(new LayoutProperty("border-right"));
        al.add(new LayoutProperty("border-bottom"));
        al.add(new LayoutProperty("border-left"));
        
        return al;
    });
    
    
    
}
