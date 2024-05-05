package LWeb.Engine;

import static LWeb.Common.Common.cast;
import LWeb.Common.Counter;
import LWeb.Common.DynArray.DynArray;
import LWeb.Common.UbFunction;
import LWeb.Engine.Constants.ConstTypes;
import LWeb.Engine.Instr.Root;
import LWeb.Engine.Instr.RootP.ResourceP.ResourcePointer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.function.Supplier;



public class Core {

    
    
    public enum RenderTypes{JAVA,OPENGL};
    public RenderTypes renderType=RenderTypes.OPENGL;
    private final DynArray<Object> resources = new DynArray<>();
    private final HashMap<String, UbFunction<?,?>> callables = new HashMap<>();
    public final HashMap<String, Object> namedApi = new HashMap<>();
    private final DynArray<Exception> errors = new DynArray<>();
    private final Constants consts = new Constants();
    private final Stack<Object> callStack = new Stack<>();
    public Runnable terminationCallback =()->{System.exit(0);};
    public int progCounter=0;
    public Thread progThread;
    private long newError=-1;
    
    public Runnable[] byteToDraw(byte o[], Counter i){
        progThread = Thread.currentThread();
        ArrayList<Runnable> rn=new ArrayList<>();        
        while(!i.ended){
            rn.add(Root.getInst(o, i, this));
            if(newError!=-1){
                System.out.println(errors.get((int)newError));
                newError=-1;
            }
        }
        return rn.toArray(new Runnable[0]);
    }
    
    public Object getResource(int id){
        Object o = resources.get(id);
        if(o instanceof ResourcePointer){
            int ptr = ((ResourcePointer)o).ptr;
            if(ptr==id)return null;
            o=getResource(ptr);
        }
        return o;
    }
    public <T> T getResource(int id, Class<T> c){
        return cast(c, getResource(id));
    }
    public Object getRawResource(int id){
        return resources.get(id);
    }
    public <T> T getRawResource(int id, Class<T> c){
        return cast(c,  resources.get(id));
    }
    public void putResource(int id, Object re){
        Object o = resources.get(id);
        if(o instanceof ResourcePointer){
            int ptr = ((ResourcePointer)o).ptr;
            if(ptr==id)return;
            putResource(ptr, re);
        }
        resources.set(id, re);
    }
    public void putRawResource(int id, Object re){
        resources.set(id, re);
    }
    public UbFunction getCallable(String name){
        return callables.get(name);
    }
    public void putCallable(String name, UbFunction<?,?> runnable){
        callables.put(name, runnable);
    }
    public void reportError(int pos, Exception ex){
        newError=pos;
        errors.set(pos, ex);
    }
    public void putStack(Object o){
        callStack.push(o);
    }
    public <T> T popStack(Class<T> c){
        return cast(c, callStack.pop());
    }
    public <T> T peekStack(Class<T> c){
        return cast(c, callStack.peek());
    }
    public <T> T getConstant(ConstTypes id, Class<T> cl){
        return (T)cast(cl, consts.getConstant(id));
    }
    
}
