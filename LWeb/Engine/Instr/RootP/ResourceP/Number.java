package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToFloat;
import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;

public class Number {
    public static Object getRsc(ByteCounter i, Core c){
        return byteToFloat(new byte[]{i.next(),i.next(),i.next(),i.next()});
    }
}
