package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.itb;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceInst;

public class Curve {
    public static ResourceInst.RByteCol getBytes(int Rcondition){
        return new ResourceInst.RByteCol(23,  ib(1), itb(Rcondition));
    }
    public static Object getRsc(ByteCounter i, Core c){
        int length = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int x = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int y = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        
        Object resource = null;
        return resource;
    }
}
