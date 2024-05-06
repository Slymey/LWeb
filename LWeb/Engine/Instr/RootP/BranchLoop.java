package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceP.Loop;

public class BranchLoop {
    public static Runnable getInst(ByteCounter i, Core c){
        int id = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        return ()->{
            c.progCounter=c.getResource(id, Loop.class).proccessLoop();
        };
    }
}
