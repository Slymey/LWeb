package LWeb.Engine.Instr.RootP.ResourceP;

import LWeb.Common.Common;
import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.cast;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.istb;
import static LWeb.Common.Common.itb;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceInst;
import java.util.Arrays;


public class ResourcePointer {
    public static ResourceInst.RByteCol getBytes(int Rresource){
        return new ResourceInst.RByteCol(12, itb(Rresource));
    }
    
    public int ptr;
    Core c;
    public ResourcePointer(int ptr, Core c){
        this.ptr=ptr;
        this.c=c;
    }
    public Object resolve(){
        return c.getResource(ptr);
    }
    public<T> T resolve(Class<T> cl){
        return c.getResource(ptr, cl);
    }
    public void update(Object re){
        c.putResource(ptr, re);
    }
    public Object derefrence(){
        return c.getRawResource(ptr);
    }
    public void assign(Object re){
        c.putRawResource(ptr, re);
    }
    
    public static Object getRsc(ByteCounter i, Core c){
        int ptr = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        return new ResourcePointer(ptr, c);
    }
}
