

package LWeb.Compiler.Components;

import static LWeb.Common.Common.inList;
import static LWeb.Common.Common.lognm;
import LWeb.Compiler.Components.LayoutProperty.Layout;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StyleProperty {
    //statics 
    private static Class<?>[] allTypes = TypeProvider.class.getDeclaredClasses();
    //for a property instantiate one of this
    public final String name;
    final TypeProvider type;
    final BiFunction<TypeProvider[],Integer,Property[]> cast;//mapings for eg, border: 10px solid red -> border-width:10px, ...
    public final BiConsumer<Property, HashMap<String, Object>> layouts;
    
    
    
    
    /**
     * Create new Style property erg.:
     * border, width, ...
     *
     */
    public <T> StyleProperty(String name, TypeProvider type, BiFunction<TypeProvider[],Integer,Property[]> reCasts, BiConsumer<Property, HashMap<String, Object>> layouts){
        this.name=name;
        this.type=type;
        this.cast=reCasts;
        this.layouts=layouts;
    }
    
    //do somethin for multi inputs
    public Property getPropertys(){
        return new Property(this.type,0,this);
    }
    public Property getPropertys(int priority){
        return new Property(this.type,priority,this);
    }
    public Property[] getProperties(TypeProvider[] value){
        return getProperty1(value,0);
    }
    public Property[] getProperties(TypeProvider[] value, int priority){
//        System.out.println(lognm()+"?? "+value);
        return getProperty1(value, priority);
    }
    
    /**
     * decomposes a property into component properties
     * eg. border: 10px solid red -> border-width:10px, ...
     */
    private Property[] getProperty1(TypeProvider[] value, int priority){
        if(value.length==0)return new Property[]{};
        if(cast==null)return new Property[]{new Property(value[0], priority, this)};
        Property out[] = cast.apply(value, priority);
        
        return out;
    }
    public static class Property {
        final TypeProvider data;
        public final StyleProperty from;
        int priority=0;
        
        private Property(TypeProvider data, int priority, StyleProperty from){
            if(from.type.getClass()!=data.getClass())data=from.type.copy();
            this.data=data;
            this.priority=priority;
            this.from=from;
        }
        public String toString(){
            return from.name+":"+data+((priority==0)?"":" !p:"+priority);
        }
    }
}
