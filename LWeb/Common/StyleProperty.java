

package Common;

import static Common.Common.inList;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StyleProperty {
    //statics 
    private static Class<?>[] allTypes = TypeProvider.class.getDeclaredClasses();
    //for a property instantiate one of this
    final String name;
    final TypeProvider type;
    final HashMap<TypeProvider[],BiFunction<TypeProvider[],Integer,Property[]>> reCasts;//mapings for eg, border: 10px solid red -> border-width:10px, ...
    
    
    
    
    
    /**
     * Create new Style property erg.:
     * border, width, ...
     *
     */
    public <T> StyleProperty(String name, TypeProvider type, HashMap<TypeProvider[],BiFunction<TypeProvider[],Integer,Property[]>> reCasts){
        this.name=name;
        this.type=type;
        this.reCasts=reCasts;
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
        return getProperty1(value, priority);
    }
    
    /**
     * decomposes a property into component properties
     * eg. border: 10px solid red -> border-width:10px, ...
     */
    private Property[] getProperty1(TypeProvider[] value, int priority){
        if(value.length==0)return null;
        if(reCasts==null)return new Property[]{new Property(value[0], priority, this)};
        //&&!value[0].getClass().equals(this.type.getClass())
        
        
        return null;
    }
    public class Property {
        final TypeProvider data;
        final StyleProperty from;
        int priority=0;
        
        private Property(TypeProvider data, int priority, StyleProperty from){
            this.data=data;
            this.priority=priority;
            this.from=from;
        }
    }
}
