package LWeb.Engine.Instr.RootP.ResourceP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import java.util.Arrays;

public class PlainText {
    public static Object getRsc(byte o[], Counter i){
        int length = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        return new String(Arrays.copyOfRange(o, i.c, i.inc(length)));
    }
}
