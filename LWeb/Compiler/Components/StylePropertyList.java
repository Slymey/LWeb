package LWeb.Compiler.Components;

import LWeb.Compiler.Components.StyleProperty;
import static LWeb.Common.Common.*;
import LWeb.Compiler.Components.StyleProperty.Property;
import LWeb.Compiler.Components.TypeProvider.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;


public class StylePropertyList {
    static ArrayList<StyleProperty> list = sg(()->{
        ArrayList<StyleProperty> al= new ArrayList<>();
        list=al;
        al.add(new StyleProperty("margin-top", new PropLength(0,"px"), null));
        al.add(new StyleProperty("margin-right", new PropLength(0,"px"), null));
        al.add(new StyleProperty("margin-bottom", new PropLength(0,"px"), null));
        al.add(new StyleProperty("margin-left", new PropLength(0,"px"), null));
        al.add(new StyleProperty("margin", new PropLength(0,"px"), sg(()->{
            //<editor-fold defaultstate="collapsed" desc="cast">
            BiFunction<TypeProvider[],Integer,Property[]> cast = (TypeProvider[] t, Integer p) -> {
//                        System.out.println(lognm()+"?? "+t);
//                        System.out.println(lognm()+"??2 "+byi(t));
                if(t.length==0)
                    return null;
                if(t.length==1)
                    return flatten(new Property[][]{
                        getByName("margin-top",byi(t),p),
                        getByName("margin-right",byi(t),p),
                        getByName("margin-bottom",byi(t),p),
                        getByName("margin-left",byi(t),p)
                    });
                if(t.length==2)
                    return flatten(new Property[][]{
                        getByName("margin-top",byi(t,0),p),
                        getByName("margin-right",byi(t,1),p),
                        getByName("margin-bottom",byi(t,0),p),
                        getByName("margin-left",byi(t,1),p)
                    });
                if(t.length==3)
                    return flatten(new Property[][]{
                        getByName("margin-top",byi(t,0),p),
                        getByName("margin-right",byi(t,1),p),
                        getByName("margin-bottom",byi(t,0),p),
                        getByName("margin-left",byi(t,2),p)
                    });
                return flatten(new Property[][]{
                    getByName("margin-top",byi(t,0),p),
                        getByName("margin-right",byi(t,1),p),
                        getByName("margin-bottom",byi(t,2),p),
                        getByName("margin-left",byi(t,3),p)
                });
            };
            return cast;
            //</editor-fold>
        })));
        al.add(new StyleProperty("width", new PropLength(0,"px"), null));
        al.add(new StyleProperty("height", new PropLength(0,"px"), null));
        al.add(new StyleProperty("background-color", new PropColor(0), null));
        
        return al;
    });
    
    public static StyleProperty getByName(String name){
        int ind = inList(name, list,(StyleProperty s)->{
            return s.name;
        },(String s, String s2)->{
            return s.compareTo(s2);
        });
        if(ind<0)return null;
        return list.get(ind);
    }
    public static Property[] getByName(String name, TypeProvider[] tp, int priority){
        int ind = inList(name, list,(StyleProperty s)->{
            return s.name;
        },(String s, String s2)->{
            return s.compareTo(s2);
        });
        if(ind<0){
            StyleProperty sc = new StyleProperty(name, tp[0], null);
            list.add(sc);
            return sc.getProperties(tp, priority);
        }
        return list.get(ind).getProperties(tp, priority);
    }
}
