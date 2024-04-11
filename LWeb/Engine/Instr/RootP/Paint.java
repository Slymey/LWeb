package Engine.Instr.RootP;

import static Common.Common.sg;
import Common.Counter;
import java.util.function.Supplier;
import Engine.Instr.RootP.PaintP.*;
import Engine.Instr.RootP.PaintP.String;

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
