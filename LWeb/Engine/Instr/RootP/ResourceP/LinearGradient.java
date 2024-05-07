package LWeb.Engine.Instr.RootP.ResourceP;

import LWeb.Common.Color;
import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.itb;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceInst;

public class LinearGradient {
    public static ResourceInst.RByteCol getBytes(int Rbox, int Rcolor1, int Rcolor2){
        return new ResourceInst.RByteCol(15, itb(Rbox),itb(Rcolor1), itb(Rcolor2));
    }
    
    Box b;
    Box o;
    Box a;
    Box f;
    Box p;
    
    public static Object getRsc(ByteCounter i, Core c){//TODO
        int box = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int col1 = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int col2 = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        
        Object resource = null;
        return resource;
    }
}
