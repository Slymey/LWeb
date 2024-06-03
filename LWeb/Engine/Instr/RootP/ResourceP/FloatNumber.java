package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToFloat;
import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.ftb;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.itb;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceInst;

public class FloatNumber {
    public static ResourceInst.RByteCol getBytes(float number){
        return new ResourceInst.RByteCol(9,  ftb(number));
    }
    
    public static Object getRsc(ByteCounter i, Core c){
        return byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()});
    }
}
