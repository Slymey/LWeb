package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;

public class WholeNumber {
    public static Object getRsc(ByteCounter i, Core c){
        return byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
    }
}
