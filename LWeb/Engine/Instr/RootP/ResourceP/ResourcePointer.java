package LWeb.Engine.Instr.RootP.ResourceP;

import LWeb.Engine.Core;


public class ResourcePointer {
    public final int ptr;
    public ResourcePointer(int ptr){
        this.ptr=ptr;
    }
    public Object resolve(){
        return Core.getResource(ptr);
    }
    public<T> T resolve(Class<T> c){
        return Core.getResource(ptr, c);
    }
}
