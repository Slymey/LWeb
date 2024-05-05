package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToFloat;
import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Engine.Core;

public class Number {
    public static Object getRsc(byte[] o, Counter i, Core c){
        return byteToFloat(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
    }
}
