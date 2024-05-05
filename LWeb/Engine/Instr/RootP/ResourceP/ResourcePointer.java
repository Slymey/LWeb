package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Engine.Core;
import java.util.Arrays;


public class ResourcePointer {
    public int ptr;
    public ResourcePointer(int ptr){
        this.ptr=ptr;
    }
    public Object resolve(Core c){
        return c.getResource(ptr);
    }
    public<T> T resolve(Class<T> c, Core co){
        return co.getResource(ptr, c);
    }
    
    public static Object getRsc(byte[] o, Counter i, Core c){
        int ptr = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        return new ResourcePointer(ptr);
    }
}
