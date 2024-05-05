package LWeb.Engine.Instr.RootP.ResourceP;

import LWeb.Common.Color;
import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Engine.Core;

public class LinearGradient {
    Box b;
    Box o;
    Box a;
    Box f;
    Box p;
    
    public static Object getRsc(byte[] o, Counter i, Core c){
        int box = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int col1 = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int col2 = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        
        Object resource = null;
        return resource;
    }
}
