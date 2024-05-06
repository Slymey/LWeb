package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceP.Condition;


public class BranchIfPtr {
    
    public static Runnable getInst(ByteCounter i, Core c){
        int cond = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int ptr = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        
        return () -> {
            if(cond==0||c.getResource(cond, Condition.class).evaluate())
                c.progCounter=c.getResource(ptr, int.class)-1;
        };
    }
}
