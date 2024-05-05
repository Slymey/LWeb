package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.byteToInt;
import LWeb.Common.Counter;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.ResourceP.Condition;


public class CallIf {
    
    public static Runnable getInst(byte[] o, Counter i, Core c){
        int cond = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        int ptr = byteToInt(new byte[]{o[i.inc()],o[i.inc()],o[i.inc()],o[i.inc()]});
        
        return () -> {
            if(ptr<0){
                c.progCounter=c.popStack(int.class);
                return;
            }
            if(cond==0||c.getResource(cond, Condition.class).evaluate()){
                c.putStack(c.progCounter);
                c.progCounter=ptr-1;
            }
        };
    }
}
