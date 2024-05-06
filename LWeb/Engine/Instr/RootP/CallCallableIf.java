package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.ByteCounter;
import LWeb.Common.UbFunction;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceP.Condition;


public class CallCallableIf {
    
    public static Runnable getInst(ByteCounter i, Core c){
        int cond = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        int rnbl = byteToInt(new byte[]{i.next(),i.next(),i.next(),i.next()});
        
        return () -> {
            if(cond==0||c.getResource(cond, Condition.class).evaluate()){
                c.getResource(cond, UbFunction.class).apply();
            }
        };
    }
}
