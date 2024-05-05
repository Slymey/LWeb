package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Common.UbFunction;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceP.Condition;


public class CallCallableIf {
    
    public static Runnable getInst(byte[] o, Counter i, Core c){
        int cond = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int rnbl = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        
        return () -> {
            if(cond==0||c.getResource(cond, Condition.class).evaluate()){
                c.getResource(cond, UbFunction.class).apply();
            }
        };
    }
}
