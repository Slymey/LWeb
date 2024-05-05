package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.lognm;
import LWeb.Common.Counter;
import LWeb.Engine.Core;
import java.util.Arrays;

public class NamedApi {
    public static Runnable getInst(byte o[], Counter i, Core c){
        int id = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int length = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        String name =  new String(Arrays.copyOfRange(o, i.c, i.incp(length)));
        return ()->{
            c.namedApi.put(name, id);
        };
    }
}
