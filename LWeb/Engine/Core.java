package LWeb.Engine;

import static LWeb.Common.Common.cast;
import LWeb.Common.Counter;
import LWeb.Common.DynArray.DynArray;
import LWeb.Engine.Instr.Root;
import LWeb.Engine.Instr.RootP.ResourceP.ResourcePointer;
import java.util.ArrayList;
import java.util.HashMap;



public class Core {
    
    public enum RenderTypes{JAVA,OPENGL};
    public static RenderTypes renderType=RenderTypes.OPENGL;
    public static final DynArray<Object> resources = new DynArray<>();
    public static final HashMap<String, Runnable> callables = new HashMap<>();
    public static final DynArray<Exception> errors = new DynArray<>();
    public static long newError=-1;
    
    public static Runnable[] byteToDraw(byte o[], Counter i){
        ArrayList<Runnable> rn=new ArrayList<>();        
        while(!i.ended){
            rn.add(Root.getInst(o, i));
            if(newError!=-1){
                System.out.println(errors.get((int)newError));
                newError=-1;
            }
        }
        return rn.toArray(new Runnable[0]);
    }
    
    public static Object getResource(int id){
        Object o = Core.resources.get(id);
        if(o instanceof ResourcePointer){
            int ptr = ((ResourcePointer)o).ptr;
            if(ptr==id)return null;
            o=getResource(ptr);
        }
        return o;
    }
    public static<T> T getResource(int id, Class<T> c){
        return cast(c, getResource(id));
    }
    public static Object getRawResource(int id){
        return Core.resources.get(id);
    }
    public static<T> T getRawResource(int id, Class<T> c){
        return cast(c,  Core.resources.get(id));
    }
    public static void putResource(int id, Object re){
        Core.resources.set(id, re);
    }
    

}
