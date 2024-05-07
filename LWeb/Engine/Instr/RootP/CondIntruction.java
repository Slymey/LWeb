package LWeb.Engine.Instr.RootP;

import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.byteToInt;
import static LWeb.Common.Common.flatten;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.itb;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.Root;
import LWeb.Engine.Instr.RootP.ResourceP.Condition;

public class CondIntruction {
    public static byte[] conditional(byte[] by, int Rcond){
        return flatten(ib(22),itb(Rcond), by);
    }
    public static Runnable getInst(ByteCounter i, Core c){
        int cond = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        Runnable instr = Root.getInst(i, c);
        return ()->{
            if(c.getResource(cond, Condition.class).evaluate())instr.run();
        };
    }
}
