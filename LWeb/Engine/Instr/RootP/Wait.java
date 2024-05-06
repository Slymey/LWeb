package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;
import LWeb.Engine.Util.SimpleRemoteThread;


public class Wait {
    public static Runnable getInst(ByteCounter i, Core c){
        int id = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        return () -> {
            //System.out.println(" "+id);
        };
    }
}
