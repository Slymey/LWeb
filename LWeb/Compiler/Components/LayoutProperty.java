package LWeb.Compiler.Components;

import LWeb.Common.Common;
import static LWeb.Common.Common.cast;
import static LWeb.Common.Common.castp;
import static LWeb.Common.Common.lognm;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LayoutProperty {
    public final String name;
    public LayoutProperty(String name){
        this.name=name;
    }
    
    
    
    
    public static class Layout{
        public final LayoutProperty prop;
        public Object data;
        public Layout(LayoutProperty prop, Object data){
            this.prop=prop;
            this.data=data;
        }
        public <T> T get(Class<T> c){
            return castp(c, data);
        }
    }
}
