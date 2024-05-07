package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.flatten;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.itb;
import LWeb.Engine.Core;
import LWeb.Engine.Util.SimpleRemoteThread;


public class Wait {
    public static byte[] getBytes(int Hthread){
        return flatten(ib(8), itb(Hthread));
    }
    public static Runnable getInst(ByteCounter i, Core c){
        int id = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        return () -> {
            //System.out.println(" "+id);
        };
    }
}
