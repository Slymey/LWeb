package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Engine.Core;
import LWeb.Engine.Util.SimpleRemoteThread;


public class Wait {
    public static Runnable getInst(byte[] o, Counter i, Core c){
        int id = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        return () -> {
            //System.out.println(" "+id);
        };
    }
}
