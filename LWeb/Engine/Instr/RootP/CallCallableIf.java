package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import static LWeb.Common.Common.flatten;
import static LWeb.Common.Common.ib;
import static LWeb.Common.Common.itb;
import LWeb.Common.UbFunction;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceP.Condition;


public class CallCallableIf {
    public static byte[] getBytes(int Rcondition, int Rcallable){
        return flatten(ib(15), itb(Rcondition), itb(Rcallable));
    }
    public static Runnable getInst(ByteCounter i, Core c){
        int cond = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int rnbl = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        
        return () -> {
            if(cond==0||c.getResource(cond, Condition.class).evaluate()){
                c.getResource(rnbl, UbFunction.class).apply();
            }
        };
    }
}
