package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.ftb;
import static LWeb.Common.Common.itb;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceInst;

public class WholeNumber {
    public static ResourceInst.RByteCol getBytes(int number){
        return new ResourceInst.RByteCol(10,  itb(number));
    }
    public static Object getRsc(ByteCounter i, Core c){
        return byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
    }
}
