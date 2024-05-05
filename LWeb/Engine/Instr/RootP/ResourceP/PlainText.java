package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Engine.Core;
import java.util.Arrays;

public class PlainText {
    public static Object getRsc(byte o[], Counter i, Core c){
        int length = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        return new String(Arrays.copyOfRange(o, i.c, i.inc(length)));
    }
}
