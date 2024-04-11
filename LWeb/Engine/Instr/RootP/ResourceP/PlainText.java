package Engine.Instr.RootP.ResourceP;

import static Common.Common.byteToInt;
import Common.Counter;
import Common.Pair;
import static Common.Pair.Pair;
import java.util.Arrays;

public class PlainText {
    public static Pair<Class, Object> getRsc(byte o[], Counter i){
        int length = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        return Pair(String.class, new String(Arrays.copyOfRange(o, i.c, i.inc(length))));
    }
}
