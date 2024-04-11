package Common;

import static Common.Common.*;
import Common.StyleProperty.Property;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;


public class StylePropertyList {
    static ArrayList<StyleProperty> list = sg(()->{
        ArrayList<StyleProperty> al= new ArrayList<>();
        list=al;
        al.add(new StyleProperty("margin-top", new TypeProvider.Length(0), null));
        al.add(new StyleProperty("margin-right", new TypeProvider.Length(0), null));
        al.add(new StyleProperty("margin-bottom", new TypeProvider.Length(0), null));
        al.add(new StyleProperty("margin-left", new TypeProvider.Length(0), null));
        al.add(new StyleProperty("margin", new TypeProvider.Length(0), sg(()->{
            HashMap<TypeProvider[],BiFunction<TypeProvider[],Integer,Property[]>> casts = new HashMap<>();
            casts.put(new TypeProvider[]{
                new TypeProvider.Length(0)//margin: <length>
            }, (TypeProvider[] t, Integer p) -> {
                return flatten(new Property[][]{
                    getByName("margin-top",t,p),
                    getByName("margin-right",t,p),
                    getByName("margin-bottom",t,p),
                    getByName("margin-left",t,p)
                });
            });
            return casts;
        })));
        al.add(new StyleProperty("width", new TypeProvider.Length(0), null));
        al.add(new StyleProperty("height", new TypeProvider.Length(0), null));
        al.add(new StyleProperty("background-color", new TypeProvider.ColorType(0), null));
        
        return al;
    });
    
    public static StyleProperty getByName(String name, int i){
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
        if(ind<0)return null;
        return list.get(ind).getProperties(tp, priority);
    }
}
