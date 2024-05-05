package LWeb.Engine.Instr.RootP;

import static LWeb.Common.Common.sg;
import LWeb.Common.Counter;
import LWeb.Engine.Core;
import LWeb.Engine.Instr.RootP.PaintP.Fill;
import LWeb.Engine.Instr.RootP.PaintP.None;
import LWeb.Engine.Instr.RootP.PaintP.String;
import java.util.function.Supplier;

public class Paint {
    public static Runnable getInst(byte[] o, Counter i, Core c){
        byte inst = o[i.inc()];
        return sg((Supplier<Runnable>[])new Supplier[]{
                ()->None.getInst(o, i, c),     //0
                ()->Fill.getInst(o, i, c),      //1
                ()->String.getInst(o, i, c),      //2
            }, inst);
        
        
    }
}
