package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.flatten;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.itb;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceP.Loop;

public class BranchLoop {
    public static byte[] getBytes(int RloopId){
        return flatten(ib(18), itb(RloopId));
    }
    public static Runnable getInst(ByteCounter i, Core c){
        int id = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        return ()->{
            c.progCounter=c.getResource(id, Loop.class).proccessLoop();
        };
    }
}
