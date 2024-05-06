package LWeb.Engine.Instr.RootP.ResourceP;

import LWeb.Common.Color;
import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;

public class LinearGradient {
    Box b;
    Box o;
    Box a;
    Box f;
    Box p;
    
    public static Object getRsc(ByteCounter i, Core c){
        int box = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int col1 = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int col2 = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        
        Object resource = null;
        return resource;
    }
}
