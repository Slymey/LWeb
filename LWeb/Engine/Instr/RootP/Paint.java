package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.sg;
import LWeb.Common.Counter;
import java.util.function.Supplier;
import LWeb.Engine.Instr.RootP.PaintP.*;
import LWeb.Engine.Instr.RootP.PaintP.String;

public class Paint {
    public static Runnable getInst(byte[] o, Counter i){
        byte inst = o[i.inc()];
        return sg((Supplier<Runnable>[])new Supplier[]{
                ()->None.getInst(o, i),     //0
                ()->Fill.getInst(o, i),      //1
                ()->String.getInst(o, i)      //2
            }, inst);
        
        
    }
}
